package com.superdev.toy.app.domain.studyRoom;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;

/**
 * Created by kimyc on 15/08/2019.
 */
@Embeddable
@NoArgsConstructor
public class StudyRoomId {
    private String id;

    public StudyRoomId(String id){
        this.id = id;
    }

    public String idString() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudyRoomId that = (StudyRoomId) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "StudyRoomId{" +
                "id='" + id + '\'' +
                '}';
    }
}
