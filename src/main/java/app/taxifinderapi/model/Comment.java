package app.taxifinderapi.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "comment_id")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;

    @Column(name = "message", length = 100000)
    private String message;
    private LocalDate createdDate;
    private LocalTime createdTime;
    @OneToMany(mappedBy = "comment")
    private List<ReplyComment> replyComments;

    public List<ReplyComment> getReplayComments() {
        return replyComments;
    }

    public void setReplayComments(List<ReplyComment> replyComments) {
        this.replyComments = replyComments;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalTime createdTime) {
        this.createdTime = createdTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;
    @ManyToOne
    @JoinTable(name = "user_id")
    private User user;






    public Comment() {
    }

    public Comment(String message, Trip trip) {
        this.message = message;
        this.trip = trip;
    }

    public Long getComment_id() {
        return comment_id;
    }

    public void setComment_id(Long comment_id) {
        this.comment_id = comment_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }
}
