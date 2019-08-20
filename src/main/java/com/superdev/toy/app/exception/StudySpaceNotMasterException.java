package com.superdev.toy.app.exception;

import com.superdev.toy.app.domain.User;
import com.superdev.toy.app.domain.studySpace.StudySpace;
import com.superdev.toy.web.domain.ErrorCodes;

/**
 * Created by kimyc on 15/08/2019.
 */
public class StudySpaceNotMasterException extends BaseException{
    public StudySpaceNotMasterException(User user, StudySpace studySpace){
        super(ErrorCodes.STUDY_ROOM_NOT_MASTER, "[" + user.getUsername() +"] User not master of [" + studySpace.title() + "] study room");
    }
}
