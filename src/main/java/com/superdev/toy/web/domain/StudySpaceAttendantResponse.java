package com.superdev.toy.web.domain;

import com.superdev.toy.app.domain.studyRoom.StudySpaceAttendant;
import lombok.Data;

/**
 * Created by kimyc on 15/08/2019.
 */
@Data
public class StudySpaceAttendantResponse extends SuccessResponse<StudySpaceAttendantRequest> {
    public StudySpaceAttendantResponse(StudySpaceAttendantRequest request){
        setData(request);
    }

    public StudySpaceAttendantResponse(StudySpaceAttendant studySpaceAttendant){
        setData(new StudySpaceAttendantRequest(studySpaceAttendant));
    }
}
