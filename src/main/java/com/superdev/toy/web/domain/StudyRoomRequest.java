package com.superdev.toy.web.domain;

import com.superdev.toy.app.domain.studyRoom.StudyRoom;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kimyc on 15/08/2019.
 */
@Data
@NoArgsConstructor
public class StudyRoomRequest {
    private String studyRoomId;
    private String title;
    private String description;
    private String masterNm;
    private List<UserRequest> attendants;
    private List<UserRequest> operators;
    private List<StudyRoomAttendantRequest> requests;

    public StudyRoomRequest(StudyRoom studyRoom){
        this.studyRoomId = studyRoom.studyRoomId().idString();
        this.masterNm = studyRoom.master().getUserNm();
        this.title = studyRoom.title();
        this.description = studyRoom.description();
        this.attendants = studyRoom.attendants().stream().map(i -> new UserRequest(i)).collect(Collectors.toList());
        this.operators = studyRoom.operators().stream().map(i->new UserRequest(i)).collect(Collectors.toList());
        this.requests = studyRoom.studyRoomAttendantRequests().stream().map(i->new StudyRoomAttendantRequest(i)).collect(Collectors.toList());

    }

}
