package com.superdev.toy.web.domain;

import com.superdev.toy.app.domain.studyRoom.StudyRoom;
import lombok.Data;

/**
 * Created by kimyc on 15/08/2019.
 */
@Data
public class StudyRoomResponse<T> extends SuccessResponse {
    public StudyRoomResponse(T request){
        setData(request);
    }

    public StudyRoomResponse(StudyRoom studyRoom){
        setData(new StudyRoomRequest(studyRoom));
    }
}
