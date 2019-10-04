package com.superdev.toy.web.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kimyc on 15/08/2019.
 */
@Data
@NoArgsConstructor
public class StudySpaceInfo {
    private String studyRoomId;
    private String title;
    private String description;
    private String masterId;
    private int category1;
    private int category2;
    private int category3;
    private List<UserRequest> attendants;
    private List<UserRequest> operators;
    private List<StudySpaceAttendantRequest> requests;

    public StudySpaceInfo(com.superdev.toy.app.domain.studySpace.StudySpace studySpace){
        this.studyRoomId = studySpace.studySpaceId().idString();
        this.masterId = studySpace.master().getUserId();
        this.title = studySpace.title();
        this.description = studySpace.description();
        this.category1 = studySpace.category1();
        this.category2 = studySpace.category2();
        this.category3 = studySpace.category3();
        this.attendants = studySpace.attendants().stream().map(i -> new UserRequest(i)).collect(Collectors.toList());
        this.operators = studySpace.operators().stream().map(i->new UserRequest(i)).collect(Collectors.toList());
        this.requests = studySpace.studySpaceAttendantRequests().stream().map(i->new StudySpaceAttendantRequest(i)).collect(Collectors.toList());

    }

}
