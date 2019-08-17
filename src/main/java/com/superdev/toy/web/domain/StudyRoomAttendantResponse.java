package com.superdev.toy.web.domain;

import com.superdev.toy.app.domain.studyRoom.StudyRoomAttendant;
import lombok.Data;

/**
 * Created by kimyc on 15/08/2019.
 */
@Data
public class StudyRoomAttendantResponse extends SuccessResponse<StudyRoomAttendantRequest> {
    public StudyRoomAttendantResponse(StudyRoomAttendantRequest request){
        setData(request);
    }

    public StudyRoomAttendantResponse(StudyRoomAttendant studyRoomAttendant){
        setData(new StudyRoomAttendantRequest(studyRoomAttendant));
    }
}
