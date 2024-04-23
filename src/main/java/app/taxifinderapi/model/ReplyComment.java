package app.taxifinderapi.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "replayCommentId")
public class ReplyComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replayCommentId;
    @Column(name = "message", length = 100000)
    private String message;
    private LocalDate createdDate;
    private LocalTime createdTime;
    @ManyToOne()
    @JoinColumn(name = "comment")
    private Comment comment;

    @ManyToOne()
    @JoinColumn(name = "user")
    private User user;

    public Long getReplayCommentId() {
        return replayCommentId;
    }

    public void setReplayCommentId(Long replayCommentId) {
        this.replayCommentId = replayCommentId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
