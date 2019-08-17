package com.superdev.toy.app.exception;

import com.superdev.toy.app.domain.User;
import com.superdev.toy.app.domain.studyRoom.StudyRoom;
import com.superdev.toy.web.domain.ErrorCodes;

/**
 * Created by kimyc on 15/08/2019.
 */
public class StudyRoomNotMasterException extends BaseException{
    public StudyRoomNotMasterException(User user, StudyRoom studyRoom){
        super(ErrorCodes.STUDY_ROOM_NOT_MASTER, "[" + user.getUsername() +"] User not master of [" + studyRoom.title() + "] study room");
    }
}
