package com.superdev.toy.web.domain;

import lombok.Data;

/**
 * Created by kimyc on 15/08/2019.
 */
@Data
public class StudySpaceResponse<T> extends SuccessResponse {
    public StudySpaceResponse(T request){
        setData(request);
    }

    public StudySpaceResponse(com.superdev.toy.app.domain.studySpace.StudySpace studySpace){
        setData(new StudySpaceInfo(studySpace));
    }
}
