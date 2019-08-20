package com.superdev.toy.web.domain;

import com.superdev.toy.app.domain.studyRoom.StudySpace;
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
