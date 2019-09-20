package com.superdev.toy.app.repo;

import com.superdev.toy.app.domain.User;
import com.superdev.toy.app.domain.studySpace.StudySpace;
import com.superdev.toy.app.domain.studySpace.StudySpaceId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kimyc on 15/08/2019.
 */
@Repository
public interface StudySpaceRepository extends JpaRepository<StudySpace, Long> {
    List<StudySpace> findByTitleStartingWith(String title, Pageable pageable);
    List<StudySpace> findByMaster(User master, Pageable pageable);
    StudySpace findByStudySpaceId(StudySpaceId studySpaceId);
}
