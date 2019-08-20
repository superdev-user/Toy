package com.superdev.toy.web.domain;

import com.superdev.toy.app.domain.studySpace.StudySpace;
import lombok.Data;

/**
 * Created by kimyc on 15/08/2019.
 */
@Data
public class StudySpaceSimpleRequest {
    private String studyRoomId;
    private String title;
    private String description;
    private String masterNm;

    public StudySpaceSimpleRequest(StudySpace studySpace){
        this.studyRoomId = studySpace.studySpaceId().idString();
        this.title = studySpace.title();
        this.description = studySpace.description();
        this.masterNm = studySpace.master().getUserNm();
    }
}
