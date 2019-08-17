package com.superdev.toy.app.exception;

import com.superdev.toy.app.domain.studyRoom.StudyRoomId;
import com.superdev.toy.web.domain.ErrorCodes;

/**
 * Created by kimyc on 15/08/2019.
 */
public class AlreadyParticipationStudyRoomException extends BaseException {
    public AlreadyParticipationStudyRoomException(StudyRoomId studyRoomId){
        super(ErrorCodes.ALREADY_PARTICIPATION_STUDY_ROOM, "[" + studyRoomId.idString() + "] study room already participation.");
    }
}
