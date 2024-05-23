package app.taxifinderapi.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "trip_id")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripId;

    private String attachment;

    private String price;

    @Column(name = "note", length = 100000)
    private String note;

    private Integer up_vote;

    private Integer down_vote;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "trip")
    private List<Location> location;

    @OneToMany(mappedBy = "trip")
    private List<Comment> comments = new ArrayList<>();
    
    @ManyToOne()
    @JoinColumn(name = "question")
    private Question question;
    @OneToMany(mappedBy = "trip")
    private List<Rating> ratings;

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Trip() {
    }

    public Trip(String attachment, String price, String note, Integer up_vote, Integer down_vote) {
        this.attachment = attachment;
        this.price = price;
        this.note = note;
        this.up_vote = up_vote;
        this.down_vote = down_vote;
    }

    public Long getTripId() {
        return tripId;
    }

    public void setTripId(Long tripId) {
        this.tripId = tripId;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getUp_vote() {
        return up_vote;
    }

    public void setUp_vote(Integer up_vote) {
        this.up_vote = up_vote;
    }

    public Integer getDown_vote() {
        return down_vote;
    }

    public void setDown_vote(Integer down_vote) {
        this.down_vote = down_vote;
    }

    public List<Location> getLocation() {
        return location;
    }

    public void setLocation(List<Location> location) {
        this.location = location;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
