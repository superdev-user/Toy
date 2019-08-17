package com.superdev.toy.app.domain.studyRoom;

import com.superdev.toy.app.domain.User;
import com.superdev.toy.app.exception.AlreadyParticipationStudyRoomException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kimyc on 15/08/2019.
 */
@Entity
@Table(name = "studyRoom")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudyRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "studyRoomId", unique = true, length = 40))
    private StudyRoomId studyRoomId;

    @Column(length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;


    @OneToOne
    private User master;

    @OneToMany(cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "studyRoomUserMapped", joinColumns = @JoinColumn(name = "studyRoomId"),
            inverseJoinColumns = @JoinColumn(name = "seq"))
    private Set<User> attendants;

    @OneToMany(cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "studyRoomOperatorMapped", joinColumns = @JoinColumn(name = "studyRoomId"),
            inverseJoinColumns = @JoinColumn(name = "seq"))
    private Set<User> operators;

    @OneToMany(cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "studyRoomUserMapped", joinColumns = @JoinColumn(name = "studyRoomId"),
            inverseJoinColumns = @JoinColumn(name = "seq"))
    private Set<StudyRoomAttendant> requests;


    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public void studyRoomMaster(User user){
        this.master = user;
    }

    public void studyRoomId(StudyRoomId studyRoomId){
        this.studyRoomId = studyRoomId;
    }

    public void addAttendant(User user){
        if(attendants == null){
            attendants = new HashSet<>();
        }

        attendants.add(user);
    }

    public void addOperator(User user){
        if(operators == null){
            operators = new HashSet<>();
        }

        operators.add(user);
    }

    public StudyRoomAttendant addStudyRoomAttendantRequest(User applicant){
        if(requests == null){
            requests = new HashSet<>();
        }

        StudyRoomAttendant studyRoomAttendant = StudyRoomAttendant.builder().approved(StudyRoomAttendantStatus.REQUEST).requester(applicant).studyRoom(this).build();
        if(requests.contains(studyRoomAttendant)){
            throw new AlreadyParticipationStudyRoomException(this.studyRoomId);
        }
        requests.add(studyRoomAttendant);
        return studyRoomAttendant;
    }

    public StudyRoomId studyRoomId(){
        return this.studyRoomId;
    }

    public String title(){
        return this.title;
    }

    public String description(){
        return this.description;
    }

    public Set<User> attendants(){
        if(this.attendants == null){
            return new HashSet<>();
        }
        return this.attendants;
    }

    public Set<User> operators(){
        if(this.operators == null){
            return new HashSet<>();
        }
        return this.operators;
    }

    public User master(){
        return this.master;
    }

    public Set<StudyRoomAttendant> studyRoomAttendantRequests(){
        if(this.requests == null){
            return new HashSet<>();
        }
        return this.requests;
    }

    public StudyRoom approveAttend(StudyRoomAttendant attendant){
        attendant.updateApproved(StudyRoomAttendantStatus.OK, master.getUsername());
        addAttendant(attendant.requester());
        return this;
    }

    public boolean isMaster(User user){
        return this.master == user;
    }

    public void deleteAll(){
        this.requests = null;
        this.operators = null;
        this.attendants = null;
    }

}
