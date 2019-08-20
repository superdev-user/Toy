package com.superdev.toy.app.domain.studyRoom;

import com.superdev.toy.app.domain.User;
import com.superdev.toy.app.exception.AlreadyParticipationStudySpaceException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kimyc on 15/08/2019.
 */
@Entity
@Table(name = "studySpace")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudySpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "studySpaceId", unique = true, length = 40))
    private StudySpaceId studySpaceId;

    @Column(length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;


    @OneToOne
    private User master;

    @OneToMany(cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "studySpaceUserMapped", joinColumns = @JoinColumn(name = "studySpaceId"),
            inverseJoinColumns = @JoinColumn(name = "seq"))
    private Set<User> attendants;

    @OneToMany(cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "studySpaceOperatorMapped", joinColumns = @JoinColumn(name = "studySpaceId"),
            inverseJoinColumns = @JoinColumn(name = "seq"))
    private Set<User> operators;

    @OneToMany(cascade = CascadeType.ALL)
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "studySpaceUserMapped", joinColumns = @JoinColumn(name = "studySpaceId"),
            inverseJoinColumns = @JoinColumn(name = "seq"))
    private Set<StudySpaceAttendant> requests;


    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public void studySpaceMaster(User user){
        this.master = user;
    }

    public void studySpaceId(StudySpaceId studySpaceId){
        this.studySpaceId = studySpaceId;
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

    public StudySpaceAttendant addStudySpaceAttendantRequest(User applicant){
        if(requests == null){
            requests = new HashSet<>();
        }

        StudySpaceAttendant studySpaceAttendant = StudySpaceAttendant.builder().approved(StudySpaceAttendantStatus.REQUEST).requester(applicant).studySpace(this).build();
        if(requests.contains(studySpaceAttendant)){
            throw new AlreadyParticipationStudySpaceException(this.studySpaceId);
        }
        requests.add(studySpaceAttendant);
        return studySpaceAttendant;
    }

    public StudySpaceId studySpaceId(){
        return this.studySpaceId;
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

    public Set<StudySpaceAttendant> studySpaceAttendantRequests(){
        if(this.requests == null){
            return new HashSet<>();
        }
        return this.requests;
    }

    public StudySpace approveAttend(StudySpaceAttendant attendant){
        attendant.updateApproved(StudySpaceAttendantStatus.OK, master.getUsername());
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
