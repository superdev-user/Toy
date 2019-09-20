package com.superdev.toy.web.domain;

import com.superdev.toy.app.domain.studySpace.StudySpace;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kimyc on 15/08/2019.
 */
@Data
@NoArgsConstructor
public class StudySpaceRequest {
    private String title;
    private String description;

    public StudySpaceRequest(StudySpace studySpace){
        this.title = studySpace.title();
        this.description = studySpace.description();
    }

}
