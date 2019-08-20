package com.superdev.toy.web.domain;

import com.superdev.toy.app.domain.studySpace.StudySpace;
import lombok.Data;

/**
 * Created by kimyc on 15/08/2019.
 */
@Data
public class StudySpaceResponse<T> extends SuccessResponse {
    public StudySpaceResponse(T request){
        setData(request);
    }

    public StudySpaceResponse(StudySpace studySpace){
        setData(new StudySpaceRequest(studySpace));
    }
}
