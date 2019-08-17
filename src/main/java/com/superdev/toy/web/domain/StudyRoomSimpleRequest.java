package com.superdev.toy.web.domain;

import com.superdev.toy.app.domain.studyRoom.StudyRoom;
import lombok.Data;

/**
 * Created by kimyc on 15/08/2019.
 */
@Data
public class StudyRoomSimpleRequest {
    private String studyRoomId;
    private String title;
    private String description;
    private String masterNm;

    public StudyRoomSimpleRequest(StudyRoom studyRoom){
        this.studyRoomId = studyRoom.studyRoomId().idString();
        this.title = studyRoom.title();
        this.description = studyRoom.description();
        this.masterNm = studyRoom.master().getUserNm();
    }
}
