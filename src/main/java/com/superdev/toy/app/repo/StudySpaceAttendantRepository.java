package com.superdev.toy.app.repo;

import com.superdev.toy.app.domain.User;
import com.superdev.toy.app.domain.studyRoom.StudySpace;
import com.superdev.toy.app.domain.studyRoom.StudySpaceAttendant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by kimyc on 15/08/2019.
 */
@Repository
public interface StudySpaceAttendantRepository extends JpaRepository<StudySpaceAttendant, Long> {
    int deleteByRequesterAndStudySpace(User requester, StudySpace studySpace);

    StudySpaceAttendant findByRequesterAndStudySpace(User requester, StudySpace studySpace);
}
