package com.superdev.toy.app.service;

import com.superdev.toy.app.domain.User;
import com.superdev.toy.app.domain.studySpace.*;
import com.superdev.toy.app.exception.StudySpaceNotFoundException;
import com.superdev.toy.app.exception.StudySpaceNotMasterException;
import com.superdev.toy.app.repo.StudySpaceAttendantRepository;
import com.superdev.toy.app.repo.StudySpaceRepository;
import com.superdev.toy.web.domain.*;
import com.superdev.toy.web.domain.StudySpaceInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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



    private StudySpaceResponse saveStudySpace(StudySpace studySpace, String authName){
        log.info("{} : {}", studySpace.title(), studySpace.description());
        User master = (User) userService.loadUserByUsername(authName);
        studySpace.studySpaceMaster(master);
        studySpace.studySpaceId(new StudySpaceId(idGenerator.next()));
        StudySpace newStudySpace = studySpaceRepository.save(studySpace);

        return new StudySpaceResponse(newStudySpace);
    }

    @Transactional
    public StudySpaceResponse saveStudySpace(StudySpaceRequest request, String authName){
        User master = (User) userService.loadUserByUsername(authName);
        StudySpace studySpace = StudySpace.builder().title(request.getTitle()).description(request.getDescription()).category1(request.getCategory1()).category2(request.getCategory2()).category3(request.getCategory3()).master(master).build();
        return saveStudySpace(studySpace, authName);
    }

    @Transactional(readOnly = true)
    public StudySpaceListResponse findStudySpaceList(String title, int page, int pageSize){
        PageRequest pageRequest = PageRequest.of(page, pageSize, new Sort(Sort.Direction.DESC, "createdAt"));
        List<StudySpace> studySpaces;
        if(!StringUtils.isEmpty(title)){
            studySpaces = studySpaceRepository.findByTitleStartingWith(title, pageRequest);
        }else{
            studySpaces = studySpaceRepository.findAll(pageRequest).getContent();
        }
        return new StudySpaceListResponse(new StudySpaceListResponse.StudyRoomListRequest(studySpaces.stream().map(i->new StudySpaceInfo(i)).collect(Collectors.toList())));
    }

    @Transactional
    public StudySpaceAttendantResponse participationStudySpace(StudySpaceId studySpaceId, String authName){
        User applicant = (User) userService.loadUserByUsername(authName);
        StudySpace studySpace = studySpaceRepository.findByStudySpaceId(studySpaceId);

        if(studySpace == null){
            throw new StudySpaceNotFoundException(studySpaceId);
        }

        StudySpaceAttendant attendant = studySpace.addStudySpaceAttendantRequest(applicant);
        return new StudySpaceAttendantResponse(attendant);
    }

    @Transactional
    public void unParticipationSpace(StudySpaceId studySpaceId, String authName){
        User applicant = (User) userService.loadUserByUsername(authName);
        StudySpace studySpace = studySpaceRepository.findByStudySpaceId(studySpaceId);

        if(studySpace == null){
            throw new StudySpaceNotFoundException(studySpaceId);
        }

        studySpaceAttendantRepository.deleteByRequesterAndStudySpace(applicant, studySpace);
    }

    @Transactional
    public StudySpaceResponse approveStudySpaceAttendant(String attendantNm, StudySpaceId studySpaceId, String authName){
        User attendant = (User) userService.loadUserByUsername(attendantNm);
        User master = (User) userService.loadUserByUsername(authName);
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
    public StudySpaceResponse unApproveStudySpaceAttendant(String attendantNm, StudySpaceId studySpaceId, String authName){
        User attendant = (User) userService.loadUserByUsername(attendantNm);
        User master = (User) userService.loadUserByUsername(authName);
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
    public StudySpaceResponse findStudySpaceInfo(StudySpaceId studySpaceId, String authName){
        StudySpace studySpace = studySpaceRepository.findByStudySpaceId(studySpaceId);
        if(studySpace == null){
            throw new StudySpaceNotFoundException(studySpaceId);
        }
        return new StudySpaceResponse(studySpace);
    }

    @Transactional
    public void deleteStudySpace(String authName, StudySpaceId studySpaceId){
        User master = (User) userService.loadUserByUsername(authName);
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

    @Transactional
    public StudySpaceResponse updateStudySpace(StudySpaceRequest request, StudySpaceId studySpaceId, String authName){
        User master = (User) userService.loadUserByUsername(authName);

        StudySpace studySpace = studySpaceRepository.findByStudySpaceId(studySpaceId);
        if(studySpace == null){
            throw new StudySpaceNotFoundException(studySpaceId);
        }
        if(!studySpace.isMaster(master)){
            throw new StudySpaceNotMasterException(master, studySpace);
        }

        studySpace.setTitle(request.getTitle());
        studySpace.setDescription(request.getDescription());
        studySpace.setCategory1(request.getCategory1());
        studySpace.setCategory2(request.getCategory2());
        studySpace.setCategory3(request.getCategory3());

        return new StudySpaceResponse(studySpaceRepository.save(studySpace));
    }


    /*


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

     */
}
