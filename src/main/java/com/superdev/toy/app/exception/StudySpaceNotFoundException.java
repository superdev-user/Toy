package com.superdev.toy.app.exception;

import com.superdev.toy.app.domain.studyRoom.StudySpaceId;
import com.superdev.toy.web.domain.ErrorCodes;

/**
 * Created by kimyc on 15/08/2019.
 */
public class StudySpaceNotFoundException extends BaseException {
    public StudySpaceNotFoundException(StudySpaceId studySpaceId){
        super(ErrorCodes.STUDY_ROOM_NOT_FOUND, "StudySpace not found [" + studySpaceId.idString() +"]");
    }
}
