package com.superdev.toy.web.resource;

import com.superdev.toy.app.domain.studyRoom.StudyRoomId;
import com.superdev.toy.app.exception.AlreadyParticipationStudyRoomException;
import com.superdev.toy.app.exception.StudyRoomNotFoundException;
import com.superdev.toy.app.exception.StudyRoomNotMasterException;
import com.superdev.toy.app.service.StudyRoomService;
import com.superdev.toy.web.domain.*;
import com.superdev.toy.web.domain.mapper.StudyRoomMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/**
 * Created by kimyc on 15/08/2019.
 */
@Api(description = "스터디 룸 API")
@RestController
@RequestMapping("studyRoom")
@Slf4j
public class StudyRoomResource {

    private StudyRoomService studyRoomService;
    private StudyRoomMapper studyRoomMapper;

    @Autowired
    public StudyRoomResource(
        StudyRoomService studyRoomService
        , StudyRoomMapper studyRoomMapper
    ){
        this.studyRoomService = studyRoomService;
        this.studyRoomMapper = studyRoomMapper;
    }

    @ApiOperation(value = "스터디 룸 생성", response = StudyRoomResponse.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공")
    })
    @PostMapping
    public ResponseEntity saveStudyRoom(
            @AuthenticationPrincipal UserDetails user
            , @RequestBody StudyRoomRequest request
            ){

        try{
            log.info("{} : {}", request.getTitle(), request.getDescription());
//            return ResponseEntity.ok(studyRoomService.saveStudyRoom(studyRoomMapper.map(request), user));
            return ResponseEntity.ok(studyRoomService.saveStudyRoom(request, user));
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "스터디 룸 정보 조회", response = StudyRoomResponse.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "success")
            , @ApiResponse(code = 404, message = "Study Room Not founded")
            , @ApiResponse(code = 406, message = "User is not master of study room")
    })
    @GetMapping("{studyRoomId}")
    public ResponseEntity findStudyRoomInfo(
            @AuthenticationPrincipal UserDetails user
            , @PathVariable(name = "studyRoomId") StudyRoomId studyRoomId
    ){
        try{
            return ResponseEntity.ok(studyRoomService.findStudyRoomInfo(studyRoomId, user));
        }catch(StudyRoomNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse(e.errCode(), e.getMessage()));
        }catch (StudyRoomNotMasterException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new SuccessResponse(e.errCode(), e.getMessage()));
        }
    }


    @ApiOperation(value = "스터디 룸 리스트 조회", response = StudyRoomListResponse.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공")
    })
    @GetMapping
    public ResponseEntity findStudyRoomList(
            @AuthenticationPrincipal UserDetails user
            , @RequestParam(name = "title", required = false) String title
            , @RequestParam(name = "page", defaultValue = "0") int page
            , @RequestParam(name = "pageSize", defaultValue = "20") int pageSize
    ){
        return ResponseEntity.ok(studyRoomService.findStudyRoomList(user, title, page, pageSize));
    }

    @ApiOperation(value = "사용자 스터디 룸 리스트 조회", response = StudyRoomListResponse.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공")
    })
    @GetMapping("list")
    public ResponseEntity findStudyRoomListForUser(
            @RequestParam(name = "title", required = false) String title
            , @RequestParam(name = "page", defaultValue = "0") int page
            , @RequestParam(name = "pageSize", defaultValue = "20") int pageSize
    ){
        return ResponseEntity.ok(studyRoomService.findStudyRoomListForUser(title, page, pageSize));
    }


    @ApiOperation(value = "스터디 룸 참가 신청", response = SuccessResponse.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공")
            , @ApiResponse(code = 404, message = "스터디 룸을 찾을 수 없습니다.")
    })
    @PutMapping("/participation/{studyRoomId}")
    public ResponseEntity participationStudyRoom(
            @AuthenticationPrincipal UserDetails user
            , @PathVariable(name = "studyRoomId")StudyRoomId studyRoomId
            ){
        try{
            studyRoomService.participationStudyRoom(studyRoomId, user);
            return ResponseEntity.ok().build();
        }catch(StudyRoomNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse(e.errCode(), e.getMessage()));
        }catch (AlreadyParticipationStudyRoomException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new SuccessResponse(e.errCode(), e.getMessage()));
        }
    }

    @ApiOperation(value = "스터디 룸 참가 신청 해제", response = SuccessResponse.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공")
            , @ApiResponse(code = 404, message = "스터디 룸을 찾을 수 없습니다.")
    })
    @DeleteMapping("/participation/{studyRoomId}")
    public ResponseEntity unParticipationStudyRoom(
            @AuthenticationPrincipal UserDetails user
            , @PathVariable(name = "studyRoomId")StudyRoomId studyRoomId
    ){
        try{
            studyRoomService.unParticipationRoom(studyRoomId, user);
            return ResponseEntity.ok().build();
        }catch(StudyRoomNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse(e.errCode(), e.getMessage()));
        }
    }


    @ApiOperation(value = "스터디 참가 신청 허가", response = StudyRoomResponse.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "success")
            , @ApiResponse(code = 404, message = "Study Room Not founded")
            , @ApiResponse(code = 406, message = "User is not master of study room")
    })
    @PutMapping("/approve/{studyRoomId}/{attendantNm}")
    public ResponseEntity approveAttendant(
            @AuthenticationPrincipal UserDetails userDetails
            , @PathVariable(name = "studyRoomId") StudyRoomId studyRoomId
            , @PathVariable(name = "attendantNm") String attendantNm
    ){

        try{
            return ResponseEntity.ok(studyRoomService.approveStudyRoomAttendant(attendantNm, studyRoomId, userDetails));
        }catch(StudyRoomNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse(e.errCode(), e.getMessage()));
        }catch (StudyRoomNotMasterException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new SuccessResponse(e.errCode(), e.getMessage()));
        }
    }

    @ApiOperation(value = "스터디 참가 신청 거부", response = StudyRoomResponse.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "success")
            , @ApiResponse(code = 404, message = "Study Room Not founded")
            , @ApiResponse(code = 406, message = "User is not master of study room")
    })
    @DeleteMapping("/approve/{studyRoomId}/{attendantNm}")
    public ResponseEntity unApproveAttendant(
            @AuthenticationPrincipal UserDetails userDetails
            , @PathVariable(name = "studyRoomId") StudyRoomId studyRoomId
            , @PathVariable(name = "attendantNm") String attendantNm
    ){

        try{
            return ResponseEntity.ok(studyRoomService.unApproveStudyRoomAttendant(attendantNm, studyRoomId, userDetails));
        }catch(StudyRoomNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse(e.errCode(), e.getMessage()));
        }catch (StudyRoomNotMasterException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new SuccessResponse(e.errCode(), e.getMessage()));
        }
    }


    @ApiOperation(value = "스터디 룸 삭제 처리", response = SuccessResponse.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "success")
            , @ApiResponse(code = 404, message = "Study Room Not founded")
            , @ApiResponse(code = 406, message = "User is not master of study room")
    })
    @DeleteMapping("{studyRoomId}")
    public ResponseEntity deleteStudyRoom(
            @AuthenticationPrincipal UserDetails user
            , @PathVariable(name = "studyRoomId") StudyRoomId studyRoomId
    ){
        try{
            studyRoomService.deleteStudyRoom(user, studyRoomId);
            return ResponseEntity.ok().build();
        }catch(StudyRoomNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse(e.errCode(), e.getMessage()));
        }catch (StudyRoomNotMasterException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new SuccessResponse(e.errCode(), e.getMessage()));
        }
    }

}
