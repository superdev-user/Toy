package com.superdev.toy.web.domain;

import com.superdev.toy.app.domain.studyRoom.StudyRoomAttendant;
import com.superdev.toy.app.domain.studyRoom.StudyRoomAttendantStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by kimyc on 15/08/2019.
 */
@Data
@NoArgsConstructor
public class StudyRoomAttendantRequest {
    private StudyRoomAttendantStatus approved;
    private UserRequest user;

    public StudyRoomAttendantRequest(StudyRoomAttendant attendant){
        this.approved = attendant.isApproved();
        this.user = new UserRequest(attendant.requester());
    }
}
