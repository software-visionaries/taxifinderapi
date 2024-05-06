package app.taxifinderapi.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // private Integer upVote;
    private boolean isUpVoted = false;
    private boolean isDownVoted = false;
    // private Integer downVote;
    @ManyToOne()
    @JoinColumn(name = "trip")
    private Trip trip;

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // public Integer getUpVote() {
    //     return upVote;
    // }

    // public void setUpVote(Integer upVote) {
    //     this.upVote = upVote;
    // }

    public boolean isUpVoted() {
        return isUpVoted;
    }

    public void setUpVoted(boolean upVoted) {
        isUpVoted = upVoted;
    }

    public boolean isDownVoted() {
        return isDownVoted;
    }

    public void setDownVoted(boolean downVoted) {
        isDownVoted = downVoted;
    }

    // public Integer getDownVote() {
    //     return downVote;
    // }

    // public void setDownVote(Integer downVote) {
    //     this.downVote = downVote;
    // }

    public Rating() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToOne
    @JoinColumn(name = "user")
    private User user;
    public Rating(User user,Trip trip) {
        this.trip = trip;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "id=" + id +
                // ", upVote=" + upVote +
                ", isUpVoted=" + isUpVoted +
                ", isDownVoted=" + isDownVoted +
                // ", downVote=" + downVote +
                ", trip=" + trip +
                ", user=" + user +
                '}';
    }
}
