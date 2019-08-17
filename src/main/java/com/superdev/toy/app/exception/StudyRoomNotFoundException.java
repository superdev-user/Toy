package com.superdev.toy.app.exception;

import com.superdev.toy.app.domain.studyRoom.StudyRoomId;
import com.superdev.toy.web.domain.ErrorCodes;

/**
 * Created by kimyc on 15/08/2019.
 */
public class StudyRoomNotFoundException extends BaseException {
    public StudyRoomNotFoundException(StudyRoomId studyRoomId){
        super(ErrorCodes.STUDY_ROOM_NOT_FOUND, "StudyRoom not found [" + studyRoomId.idString() +"]");
    }
}
