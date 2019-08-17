package com.superdev.toy.app.repo;

import com.superdev.toy.app.domain.User;
import com.superdev.toy.app.domain.studyRoom.StudyRoom;
import com.superdev.toy.app.domain.studyRoom.StudyRoomId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kimyc on 15/08/2019.
 */
@Repository
public interface StudyRoomRepository extends JpaRepository<StudyRoom, Long> {
    List<StudyRoom> findByMasterAndTitleStartingWith(String title, Pageable pageable);
    List<StudyRoom> findByTitleStartingWith(String title, Pageable pageable);
    List<StudyRoom> findByMaster(User master, Pageable pageable);
    StudyRoom findByStudyRoomId(StudyRoomId studyRoomId);
}
