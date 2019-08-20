package com.superdev.toy.web.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kimyc on 15/08/2019.
 */
@Data
public class StudySpaceListResponse<T> extends SuccessResponse {

    @Data
    public static class StudyRoomListRequest{
        private List<StudySpaceRequest> requests = new ArrayList<>();
        public StudyRoomListRequest(List<StudySpaceRequest> studyRooms){
            requests = (studyRooms == null ? new ArrayList<>() : studyRooms);
        }
    }

    @Data
    public static class StudyRoomSimpleListRequest{
        private List<StudySpaceSimpleRequest> requests = new ArrayList<>();
        public StudyRoomSimpleListRequest(List<StudySpaceSimpleRequest> studyRoomSimpleListRequests){
            requests = (studyRoomSimpleListRequests == null ? new ArrayList<>() : studyRoomSimpleListRequests);
        }
    }

    public StudySpaceListResponse(T studyRooms){
        setData(studyRooms);
    }
}
