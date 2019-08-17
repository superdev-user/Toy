package com.superdev.toy.web.domain;

import com.superdev.toy.app.domain.studyRoom.StudyRoom;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kimyc on 15/08/2019.
 */
@Data
public class StudyRoomListResponse<T> extends SuccessResponse {

    @Data
    public static class StudyRoomListRequest{
        private List<StudyRoomRequest> requests = new ArrayList<>();
        public StudyRoomListRequest(List<StudyRoomRequest> studyRooms){
            requests = (studyRooms == null ? new ArrayList<>() : studyRooms);
        }
    }

    @Data
    public static class StudyRoomSimpleListRequest{
        private List<StudyRoomSimpleRequest> requests = new ArrayList<>();
        public StudyRoomSimpleListRequest(List<StudyRoomSimpleRequest> studyRoomSimpleListRequests){
            requests = (studyRoomSimpleListRequests == null ? new ArrayList<>() : studyRoomSimpleListRequests);
        }
    }

    public StudyRoomListResponse(T studyRooms){
        setData(studyRooms);
    }
}
