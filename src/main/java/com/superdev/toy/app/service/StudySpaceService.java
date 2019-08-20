package com.superdev.toy.app.service;

import com.superdev.toy.app.domain.User;
import com.superdev.toy.app.domain.studyRoom.*;
import com.superdev.toy.app.exception.StudySpaceNotFoundException;
import com.superdev.toy.app.exception.StudySpaceNotMasterException;
import com.superdev.toy.app.repo.StudySpaceAttendantRepository;
import com.superdev.toy.app.repo.StudySpaceRepository;
import com.superdev.toy.web.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kimyc on 15/08/2019.
 */
@Service
@Slf4j
public class StudySpaceService {
    private StudySpaceRepository studySpaceRepository;
    private StudySpaceAttendantRepository studySpaceAttendantRepository;
    private UserService userService;
    private IdGenerator idGenerator;

    @Autowired
    public StudySpaceService(
            StudySpaceRepository studySpaceRepository
            , StudySpaceAttendantRepository studySpaceAttendantRepository
            , UserService userService
            , IdGenerator idGenerator
    ){
        this.studySpaceRepository = studySpaceRepository;
        this.studySpaceAttendantRepository = studySpaceAttendantRepository;
        this.userService = userService;
        this.idGenerator = idGenerator;
    }



    private StudySpaceResponse saveStudySpace(StudySpace studySpace, UserDetails user){
        log.info("{} : {}", studySpace.title(), studySpace.description());
        User master = (User) userService.loadUserByUsername(user.getUsername());
        studySpace.studySpaceMaster(master);
        studySpace.studySpaceId(new StudySpaceId(idGenerator.next()));
        StudySpace newStudySpace = studySpaceRepository.save(studySpace);

        return new StudySpaceResponse(newStudySpace);
    }

    @Transactional
    public StudySpaceResponse saveStudySpace(StudySpaceRequest request, UserDetails user){
        User master = (User) userService.loadUserByUsername(user.getUsername());
        StudySpace studySpace = StudySpace.builder().title(request.getTitle()).description(request.getDescription()).master(master).build();
        return saveStudySpace(studySpace, user);
    }

    @Transactional(readOnly = true)
    public StudySpaceListResponse findStudySpaceList(UserDetails user, String title, int page, int pageSize){
        User master = (User) userService.loadUserByUsername(user.getUsername());
        PageRequest pageRequest = PageRequest.of(page, pageSize, new Sort(Sort.Direction.DESC, "createdAt"));
        List<StudySpace> studySpaces;
        if(!StringUtils.isEmpty(title)){
            studySpaces = studySpaceRepository.findByMasterAndTitleStartingWith(title, pageRequest);
        }else{
            studySpaces = studySpaceRepository.findByMaster(master, pageRequest);
        }
        return new StudySpaceListResponse(new StudySpaceListResponse.StudyRoomListRequest(studySpaces.stream().map(i->new StudySpaceRequest(i)).collect(Collectors.toList())));
    }

    @Transactional
    public StudySpaceAttendantResponse participationStudySpace(StudySpaceId studySpaceId, UserDetails user){
        User applicant = (User) userService.loadUserByUsername(user.getUsername());
        StudySpace studySpace = studySpaceRepository.findByStudySpaceId(studySpaceId);

        if(studySpace == null){
            throw new StudySpaceNotFoundException(studySpaceId);
        }

        StudySpaceAttendant attendant = studySpace.addStudySpaceAttendantRequest(applicant);
        return new StudySpaceAttendantResponse(attendant);
    }

    @Transactional
    public void unParticipationSpace(StudySpaceId studySpaceId, UserDetails user){
        User applicant = (User) userService.loadUserByUsername(user.getUsername());
        StudySpace studySpace = studySpaceRepository.findByStudySpaceId(studySpaceId);

        if(studySpace == null){
            throw new StudySpaceNotFoundException(studySpaceId);
        }

        studySpaceAttendantRepository.deleteByRequesterAndStudySpace(applicant, studySpace);
    }

    @Transactional
    public StudySpaceResponse approveStudySpaceAttendant(String attendantNm, StudySpaceId studySpaceId, UserDetails user){
        User attendant = (User) userService.loadUserByUsername(attendantNm);
        User master = (User) userService.loadUserByUsername(user.getUsername());
        StudySpace studySpace = studySpaceRepository.findByStudySpaceId(studySpaceId);
        if(studySpace == null){
            throw new StudySpaceNotFoundException(studySpaceId);
        }

        if(!studySpace.isMaster(master)){
            throw new StudySpaceNotMasterException(master, studySpace);
        }

        studySpace.approveAttend(studySpaceAttendantRepository.findByRequesterAndStudySpace(attendant, studySpace));

        return new StudySpaceResponse(studySpace);
    }

    @Transactional
    public StudySpaceResponse unApproveStudySpaceAttendant(String attendantNm, StudySpaceId studySpaceId, UserDetails user){
        User attendant = (User) userService.loadUserByUsername(attendantNm);
        User master = (User) userService.loadUserByUsername(user.getUsername());
        StudySpace studySpace = studySpaceRepository.findByStudySpaceId(studySpaceId);
        if(studySpace == null){
            throw new StudySpaceNotFoundException(studySpaceId);
        }

        if(!studySpace.isMaster(master)){
            throw new StudySpaceNotMasterException(master, studySpace);
        }

        StudySpaceAttendant studySpaceAttendant = studySpaceAttendantRepository.findByRequesterAndStudySpace(attendant, studySpace);
        studySpaceAttendant.updateApproved(StudySpaceAttendantStatus.REJECTED, master.getUsername());

        return new StudySpaceResponse(studySpace);
    }

    @Transactional(readOnly = true)
    public StudySpaceResponse findStudySpaceInfo(StudySpaceId studySpaceId, UserDetails user){
        User master = (User) userService.loadUserByUsername(user.getUsername());
        StudySpace studySpace = studySpaceRepository.findByStudySpaceId(studySpaceId);
        if(studySpace == null){
            throw new StudySpaceNotFoundException(studySpaceId);
        }

        if(!studySpace.isMaster(master)){
            throw new StudySpaceNotMasterException(master, studySpace);
        }

        return new StudySpaceResponse(studySpace);
    }

    @Transactional(readOnly = true)
    public StudySpaceListResponse findStudySpaceListForUser(String title, int page, int pageSize){
        PageRequest pageRequest = PageRequest.of(page, pageSize, new Sort(Sort.Direction.DESC, "createdAt"));
        List<StudySpace> studySpaces;
        if(!StringUtils.isEmpty(title)){
            studySpaces = studySpaceRepository.findByTitleStartingWith(title, pageRequest);
        }else{
            studySpaces = studySpaceRepository.findAll(pageRequest).getContent();
        }
        return new StudySpaceListResponse(new StudySpaceListResponse.StudyRoomSimpleListRequest(studySpaces.stream().map(i->new StudySpaceSimpleRequest(i)).collect(Collectors.toList())));
    }

    @Transactional
    public void deleteStudySpace(UserDetails user, StudySpaceId studySpaceId){
        User master = (User) userService.loadUserByUsername(user.getUsername());
        StudySpace studySpace = studySpaceRepository.findByStudySpaceId(studySpaceId);
        if(studySpace == null){
            throw new StudySpaceNotFoundException(studySpaceId);
        }

        if(!studySpace.isMaster(master)){
            throw new StudySpaceNotMasterException(master, studySpace);
        }

        studySpace.deleteAll();

        studySpaceRepository.delete(studySpace);
    }

}
