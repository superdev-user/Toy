package com.superdev.toy.web.resource;

import com.superdev.toy.app.domain.studySpace.StudySpaceId;
import com.superdev.toy.app.exception.AlreadyParticipationStudySpaceException;
import com.superdev.toy.app.exception.StudySpaceNotFoundException;
import com.superdev.toy.app.exception.StudySpaceNotMasterException;
import com.superdev.toy.app.service.StudySpaceService;
import com.superdev.toy.web.domain.*;
import com.superdev.toy.web.domain.mapper.StudySpaceMapper;
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
@Api(description = "스터디 모임 API")
@RestController
@RequestMapping("studySpace")
@Slf4j
public class StudySpaceResource {

    private StudySpaceService studySpaceService;
    private StudySpaceMapper studySpaceMapper;

    @Autowired
    public StudySpaceResource(
            StudySpaceService studySpaceService
        , StudySpaceMapper studySpaceMapper
    ){
        this.studySpaceService = studySpaceService;
        this.studySpaceMapper = studySpaceMapper;
    }

    @ApiOperation(value = "스터디 모임 생성", response = StudySpaceResponse.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공")
    })
    @PostMapping
    public ResponseEntity saveStudySpace(
            @AuthenticationPrincipal UserDetails user
            , @RequestBody StudySpaceRequest request
            ){

        try{
            log.info("{} : {}", request.getTitle(), request.getDescription());
//            return ResponseEntity.ok(studyRoomService.saveStudySpace(studySpaceMapper.map(request), user));
            return ResponseEntity.ok(studySpaceService.saveStudySpace(request, user));
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "스터디 모임 정보 조회", response = StudySpaceResponse.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "success")
            , @ApiResponse(code = 404, message = "Study Space Not founded")
            , @ApiResponse(code = 406, message = "User is not master of study Space")
    })
    @GetMapping("{studySpaceId}")
    public ResponseEntity findStudySpaceInfo(
            @AuthenticationPrincipal UserDetails user
            , @PathVariable(name = "studySpaceId") StudySpaceId studySpaceId
    ){
        try{
            return ResponseEntity.ok(studySpaceService.findStudySpaceInfo(studySpaceId, user));
        }catch(StudySpaceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse(e.errCode(), e.getMessage()));
        }catch (StudySpaceNotMasterException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new SuccessResponse(e.errCode(), e.getMessage()));
        }
    }


    @ApiOperation(value = "스터디 모임 리스트 조회", response = StudySpaceListResponse.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공")
    })
    @GetMapping
    public ResponseEntity findStudySpaceList(
            @AuthenticationPrincipal UserDetails user
            , @RequestParam(name = "title", required = false) String title
            , @RequestParam(name = "page", defaultValue = "0") int page
            , @RequestParam(name = "pageSize", defaultValue = "20") int pageSize
    ){
        return ResponseEntity.ok(studySpaceService.findStudySpaceList(user, title, page, pageSize));
    }

    @ApiOperation(value = "사용자 스터디 모임 리스트 조회", response = StudySpaceListResponse.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공")
    })
    @GetMapping("list")
    public ResponseEntity findStudySpaceListForUser(
            @RequestParam(name = "title", required = false) String title
            , @RequestParam(name = "page", defaultValue = "0") int page
            , @RequestParam(name = "pageSize", defaultValue = "20") int pageSize
    ){
        return ResponseEntity.ok(studySpaceService.findStudySpaceListForUser(title, page, pageSize));
    }


    @ApiOperation(value = "스터디 모임 참가 신청", response = SuccessResponse.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공")
            , @ApiResponse(code = 404, message = "스터디 모임을 찾을 수 없습니다.")
    })
    @PutMapping("/participation/{studySpaceId}")
    public ResponseEntity participationStudySpace(
            @AuthenticationPrincipal UserDetails user
            , @PathVariable(name = "studySpaceId")StudySpaceId studySpaceId
            ){
        try{
            studySpaceService.participationStudySpace(studySpaceId, user);
            return ResponseEntity.ok().build();
        }catch(StudySpaceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse(e.errCode(), e.getMessage()));
        }catch (AlreadyParticipationStudySpaceException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new SuccessResponse(e.errCode(), e.getMessage()));
        }
    }

    @ApiOperation(value = "스터디 모임 참가 신청 해제", response = SuccessResponse.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공")
            , @ApiResponse(code = 404, message = "스터디 모임을 찾을 수 없습니다.")
    })
    @DeleteMapping("/participation/{studySpaceId}")
    public ResponseEntity unParticipationStudySpace(
            @AuthenticationPrincipal UserDetails user
            , @PathVariable(name = "studySpaceId")StudySpaceId studySpaceId
    ){
        try{
            studySpaceService.unParticipationSpace(studySpaceId, user);
            return ResponseEntity.ok().build();
        }catch(StudySpaceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse(e.errCode(), e.getMessage()));
        }
    }


    @ApiOperation(value = "스터디 참가 신청 허가", response = StudySpaceResponse.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "success")
            , @ApiResponse(code = 404, message = "Study Space Not founded")
            , @ApiResponse(code = 406, message = "User is not master of study space")
    })
    @PutMapping("/approve/{studySpaceId}/{attendantNm}")
    public ResponseEntity approveAttendant(
            @AuthenticationPrincipal UserDetails userDetails
            , @PathVariable(name = "studySpaceId") StudySpaceId studySpaceId
            , @PathVariable(name = "attendantNm") String attendantNm
    ){

        try{
            return ResponseEntity.ok(studySpaceService.approveStudySpaceAttendant(attendantNm, studySpaceId, userDetails));
        }catch(StudySpaceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse(e.errCode(), e.getMessage()));
        }catch (StudySpaceNotMasterException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new SuccessResponse(e.errCode(), e.getMessage()));
        }
    }

    @ApiOperation(value = "스터디 참가 신청 거부", response = StudySpaceResponse.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "success")
            , @ApiResponse(code = 404, message = "Study Space Not founded")
            , @ApiResponse(code = 406, message = "User is not master of study Space")
    })
    @DeleteMapping("/approve/{studySpaceId}/{attendantNm}")
    public ResponseEntity unApproveAttendant(
            @AuthenticationPrincipal UserDetails userDetails
            , @PathVariable(name = "studySpaceId") StudySpaceId studySpaceId
            , @PathVariable(name = "attendantNm") String attendantNm
    ){

        try{
            return ResponseEntity.ok(studySpaceService.unApproveStudySpaceAttendant(attendantNm, studySpaceId, userDetails));
        }catch(StudySpaceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse(e.errCode(), e.getMessage()));
        }catch (StudySpaceNotMasterException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new SuccessResponse(e.errCode(), e.getMessage()));
        }
    }


    @ApiOperation(value = "스터디 모임 삭제 처리", response = SuccessResponse.class)
    @ApiResponses({
            @ApiResponse(code = 200, message = "success")
            , @ApiResponse(code = 404, message = "Study Space Not founded")
            , @ApiResponse(code = 406, message = "User is not master of study Space")
    })
    @DeleteMapping("{studySpaceId}")
    public ResponseEntity deleteStudySpace(
            @AuthenticationPrincipal UserDetails user
            , @PathVariable(name = "studySpaceId") StudySpaceId studySpaceId
    ){
        try{
            studySpaceService.deleteStudySpace(user, studySpaceId);
            return ResponseEntity.ok().build();
        }catch(StudySpaceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SuccessResponse(e.errCode(), e.getMessage()));
        }catch (StudySpaceNotMasterException e){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new SuccessResponse(e.errCode(), e.getMessage()));
        }
    }

}
