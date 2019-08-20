package com.superdev.toy.app.domain.studyRoom;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;

/**
 * Created by kimyc on 15/08/2019.
 */
@Embeddable
@NoArgsConstructor
public class StudySpaceId {
    private String id;

    public StudySpaceId(String id){
        this.id = id;
    }

    public String idString() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudySpaceId that = (StudySpaceId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "StudySpaceId{" +
                "id='" + id + '\'' +
                '}';
    }
}
