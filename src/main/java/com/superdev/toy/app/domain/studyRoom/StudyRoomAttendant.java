package com.superdev.toy.app.domain.studyRoom;

import com.superdev.toy.app.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Created by kimyc on 15/08/2019.
 */
@Entity
@Table(name = "studyRoomAttendantRequest", indexes = {
        @Index(name = "studyRoomAttendantRequestIdx_01", columnList = "requester_seq", unique = true)
        , @Index(name = "studyRoomAttendantRequestRoomIdx_01", columnList = "studyRoom_id")
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyRoomAttendant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private StudyRoomAttendantStatus approved;

    @OneToOne
    private User requester;

    @ManyToOne
    private StudyRoom studyRoom;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private String updatedBy;

    public StudyRoomAttendantStatus isApproved(){
        return this.approved;
    }

    public User requester(){
        return this.requester;
    }

    public void updateApproved(StudyRoomAttendantStatus approved, String updatedBy){
        this.approved = approved;
        this.updatedBy = updatedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudyRoomAttendant attendant = (StudyRoomAttendant) o;
        return id == attendant.id &&
                approved == attendant.approved &&
                Objects.equals(requester, attendant.requester) &&
                Objects.equals(studyRoom, attendant.studyRoom);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, approved, requester, studyRoom);
    }
}
