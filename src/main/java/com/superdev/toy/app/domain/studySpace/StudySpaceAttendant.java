package com.superdev.toy.app.domain.studySpace;

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
        @Index(name = "studyRoomAttendantRequestIdx_01", columnList = "requestSeq", unique = true)
        , @Index(name = "studyRoomAttendantRequestRoomIdx_01", columnList = "studySpaceId")
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudySpaceAttendant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private StudySpaceAttendantStatus approved;

    @OneToOne
    @JoinColumn(name = "requestSeq")
    private User requester;

    @ManyToOne
    @JoinColumn(name = "studySpaceId")
    private StudySpace studySpace;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private String updatedBy;

    public StudySpaceAttendantStatus isApproved(){
        return this.approved;
    }

    public User requester(){
        return this.requester;
    }

    public void updateApproved(StudySpaceAttendantStatus approved, String updatedBy){
        this.approved = approved;
        this.updatedBy = updatedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudySpaceAttendant attendant = (StudySpaceAttendant) o;
        return id == attendant.id &&
                approved == attendant.approved &&
                Objects.equals(requester, attendant.requester) &&
                Objects.equals(studySpace, attendant.studySpace);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, approved, requester, studySpace);
    }
}
