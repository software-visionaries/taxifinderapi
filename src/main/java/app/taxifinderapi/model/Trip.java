package app.taxifinderapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trip_id;

    private String attachment;

    private String price;

    @Column(name = "note", length = 100000)
    private String note;

    private Integer up_vote;

    private Integer down_vote;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToOne(mappedBy = "trip")
    private Location location;

    @JsonIgnore
    @OneToMany(mappedBy = "trip")
    private List<Comment> comments = new ArrayList<>();

    public Trip() {
    }

    public Trip(String attachment, String price, String note, Integer up_vote, Integer down_vote) {
        this.attachment = attachment;
        this.price = price;
        this.note = note;
        this.up_vote = up_vote;
        this.down_vote = down_vote;
    }

    public Long getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(Long trip_id) {
        this.trip_id = trip_id;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
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
