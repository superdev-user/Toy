package com.superdev.toy.app.exception;

import com.superdev.toy.app.domain.studySpace.StudySpaceId;
import com.superdev.toy.web.domain.ErrorCodes;

/**
 * Created by kimyc on 15/08/2019.
 */
public class AlreadyParticipationStudySpaceException extends BaseException {
    public AlreadyParticipationStudySpaceException(StudySpaceId studySpaceId){
        super(ErrorCodes.ALREADY_PARTICIPATION_STUDY_ROOM, "[" + studySpaceId.idString() + "] study room already participation.");
    }
}
