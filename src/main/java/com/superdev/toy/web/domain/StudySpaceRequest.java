package com.superdev.toy.web.domain;

import com.superdev.toy.app.domain.studyRoom.StudySpace;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kimyc on 15/08/2019.
 */
@Data
@NoArgsConstructor
public class StudySpaceRequest {
    private String studyRoomId;
    private String title;
    private String description;
    private String masterNm;
    private List<UserRequest> attendants;
    private List<UserRequest> operators;
    private List<StudySpaceAttendantRequest> requests;

    public StudySpaceRequest(StudySpace studySpace){
        this.studyRoomId = studySpace.studySpaceId().idString();
        this.masterNm = studySpace.master().getUserNm();
        this.title = studySpace.title();
        this.description = studySpace.description();
        this.attendants = studySpace.attendants().stream().map(i -> new UserRequest(i)).collect(Collectors.toList());
        this.operators = studySpace.operators().stream().map(i->new UserRequest(i)).collect(Collectors.toList());
        this.requests = studySpace.studySpaceAttendantRequests().stream().map(i->new StudySpaceAttendantRequest(i)).collect(Collectors.toList());

    }

}
