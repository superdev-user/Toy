package com.superdev.toy.app.service;

import com.superdev.toy.app.domain.User;
import com.superdev.toy.app.domain.studyRoom.*;
import com.superdev.toy.app.exception.StudyRoomNotFoundException;
import com.superdev.toy.app.exception.StudyRoomNotMasterException;
import com.superdev.toy.app.repo.StudyRoomAttendantRepository;
import com.superdev.toy.app.repo.StudyRoomRepository;
import com.superdev.toy.web.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kimyc on 15/08/2019.
 */
@Service
@Slf4j
public class StudyRoomService {
    private StudyRoomRepository studyRoomRepository;
    private StudyRoomAttendantRepository studyRoomAttendantRepository;
    private UserService userService;
    private IdGenerator idGenerator;

    @Autowired
    public StudyRoomService(
            StudyRoomRepository studyRoomRepository
            , StudyRoomAttendantRepository studyRoomAttendantRepository
            , UserService userService
            , IdGenerator idGenerator
    ){
        this.studyRoomRepository = studyRoomRepository;
        this.studyRoomAttendantRepository = studyRoomAttendantRepository;
        this.userService = userService;
        this.idGenerator = idGenerator;
    }



    private StudyRoomResponse saveStudyRoom(StudyRoom studyRoom, UserDetails user){
        log.info("{} : {}", studyRoom.title(), studyRoom.description());
        User master = (User) userService.loadUserByUsername(user.getUsername());
        studyRoom.studyRoomMaster(master);
        studyRoom.studyRoomId(new StudyRoomId(idGenerator.next()));
        StudyRoom newStudyRoom = studyRoomRepository.save(studyRoom);

        return new StudyRoomResponse(newStudyRoom);
    }

    @Transactional
    public StudyRoomResponse saveStudyRoom(StudyRoomRequest request, UserDetails user){
        User master = (User) userService.loadUserByUsername(user.getUsername());
        StudyRoom studyRoom = StudyRoom.builder().title(request.getTitle()).description(request.getDescription()).master(master).build();
        return saveStudyRoom(studyRoom, user);
    }

    @Transactional(readOnly = true)
    public StudyRoomListResponse findStudyRoomList(UserDetails user, String title, int page, int pageSize){
        User master = (User) userService.loadUserByUsername(user.getUsername());
        PageRequest pageRequest = PageRequest.of(page, pageSize, new Sort(Sort.Direction.DESC, "createdAt"));
        List<StudyRoom> studyRooms;
        if(!StringUtils.isEmpty(title)){
            studyRooms = studyRoomRepository.findByMasterAndTitleStartingWith(title, pageRequest);
        }else{
            studyRooms = studyRoomRepository.findByMaster(master, pageRequest);
        }
        return new StudyRoomListResponse(new StudyRoomListResponse.StudyRoomListRequest(studyRooms.stream().map(i->new StudyRoomRequest(i)).collect(Collectors.toList())));
    }

    @Transactional
    public StudyRoomAttendantResponse participationStudyRoom(StudyRoomId studyRoomId, UserDetails user){
        User applicant = (User) userService.loadUserByUsername(user.getUsername());
        StudyRoom studyRoom = studyRoomRepository.findByStudyRoomId(studyRoomId);

        if(studyRoom == null){
            throw new StudyRoomNotFoundException(studyRoomId);
        }

        StudyRoomAttendant attendant = studyRoom.addStudyRoomAttendantRequest(applicant);
        return new StudyRoomAttendantResponse(attendant);
    }

    @Transactional
    public void unParticipationRoom(StudyRoomId studyRoomId, UserDetails user){
        User applicant = (User) userService.loadUserByUsername(user.getUsername());
        StudyRoom studyRoom = studyRoomRepository.findByStudyRoomId(studyRoomId);

        if(studyRoom == null){
            throw new StudyRoomNotFoundException(studyRoomId);
        }

        studyRoomAttendantRepository.deleteByRequesterAndStudyRoom(applicant, studyRoom);
    }

    @Transactional
    public StudyRoomResponse approveStudyRoomAttendant(String attendantNm, StudyRoomId studyRoomId, UserDetails user){
        User attendant = (User) userService.loadUserByUsername(attendantNm);
        User master = (User) userService.loadUserByUsername(user.getUsername());
        StudyRoom studyRoom = studyRoomRepository.findByStudyRoomId(studyRoomId);
        if(studyRoom == null){
            throw new StudyRoomNotFoundException(studyRoomId);
        }

        if(!studyRoom.isMaster(master)){
            throw new StudyRoomNotMasterException(master, studyRoom);
        }

        studyRoom.approveAttend(studyRoomAttendantRepository.findByRequesterAndStudyRoom(attendant, studyRoom));

        return new StudyRoomResponse(studyRoom);
    }

    @Transactional
    public StudyRoomResponse unApproveStudyRoomAttendant(String attendantNm, StudyRoomId studyRoomId, UserDetails user){
        User attendant = (User) userService.loadUserByUsername(attendantNm);
        User master = (User) userService.loadUserByUsername(user.getUsername());
        StudyRoom studyRoom = studyRoomRepository.findByStudyRoomId(studyRoomId);
        if(studyRoom == null){
            throw new StudyRoomNotFoundException(studyRoomId);
        }

        if(!studyRoom.isMaster(master)){
            throw new StudyRoomNotMasterException(master, studyRoom);
        }

        StudyRoomAttendant studyRoomAttendant = studyRoomAttendantRepository.findByRequesterAndStudyRoom(attendant, studyRoom);
        studyRoomAttendant.updateApproved(StudyRoomAttendantStatus.REJECTED, master.getUsername());

        return new StudyRoomResponse(studyRoom);
    }

    @Transactional(readOnly = true)
    public StudyRoomResponse findStudyRoomInfo(StudyRoomId studyRoomId, UserDetails user){
        User master = (User) userService.loadUserByUsername(user.getUsername());
        StudyRoom studyRoom = studyRoomRepository.findByStudyRoomId(studyRoomId);
        if(studyRoom == null){
            throw new StudyRoomNotFoundException(studyRoomId);
        }

        if(!studyRoom.isMaster(master)){
            throw new StudyRoomNotMasterException(master, studyRoom);
        }

        return new StudyRoomResponse(studyRoom);
    }

    @Transactional(readOnly = true)
    public StudyRoomListResponse findStudyRoomListForUser(String title, int page, int pageSize){
        PageRequest pageRequest = PageRequest.of(page, pageSize, new Sort(Sort.Direction.DESC, "createdAt"));
        List<StudyRoom> studyRooms;
        if(!StringUtils.isEmpty(title)){
            studyRooms = studyRoomRepository.findByTitleStartingWith(title, pageRequest);
        }else{
            studyRooms = studyRoomRepository.findAll(pageRequest).getContent();
        }
        return new StudyRoomListResponse(new StudyRoomListResponse.StudyRoomSimpleListRequest(studyRooms.stream().map(i->new StudyRoomSimpleRequest(i)).collect(Collectors.toList())));
    }

    @Transactional
    public void deleteStudyRoom(UserDetails user, StudyRoomId studyRoomId){
        User master = (User) userService.loadUserByUsername(user.getUsername());
        StudyRoom studyRoom = studyRoomRepository.findByStudyRoomId(studyRoomId);
        if(studyRoom == null){
            throw new StudyRoomNotFoundException(studyRoomId);
        }

        if(!studyRoom.isMaster(master)){
            throw new StudyRoomNotMasterException(master, studyRoom);
        }

        studyRoom.deleteAll();

        studyRoomRepository.delete(studyRoom);
    }

}
