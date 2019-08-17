package com.superdev.toy.app.repo;

import com.superdev.toy.app.domain.User;
import com.superdev.toy.app.domain.studyRoom.StudyRoom;
import com.superdev.toy.app.domain.studyRoom.StudyRoomAttendant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by kimyc on 15/08/2019.
 */
@Repository
public interface StudyRoomAttendantRepository extends JpaRepository<StudyRoomAttendant, Long> {
    int deleteByRequesterAndStudyRoom(User requester, StudyRoom studyRoom);

    StudyRoomAttendant findByRequesterAndStudyRoom(User requester, StudyRoom studyRoom);
}
