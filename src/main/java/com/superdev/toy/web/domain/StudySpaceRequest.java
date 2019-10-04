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
    private int category1;
    private int category2;
    private int category3;

    public StudySpaceRequest(StudySpace studySpace){
        this.title = studySpace.title();
        this.description = studySpace.description();
        this.category1 = studySpace.category1();
        this.category2 = studySpace.category2();
        this.category3 = studySpace.category3();
    }

}
