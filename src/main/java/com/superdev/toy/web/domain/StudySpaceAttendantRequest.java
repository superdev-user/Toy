package com.superdev.toy.web.domain;

import com.superdev.toy.app.domain.studySpace.StudySpaceAttendant;
import com.superdev.toy.app.domain.studySpace.StudySpaceAttendantStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by kimyc on 15/08/2019.
 */
@Data
@NoArgsConstructor
public class StudySpaceAttendantRequest {
    private StudySpaceAttendantStatus approved;
    private UserRequest user;

    public StudySpaceAttendantRequest(StudySpaceAttendant attendant){
        this.approved = attendant.isApproved();
        this.user = new UserRequest(attendant.requester());
    }
}
