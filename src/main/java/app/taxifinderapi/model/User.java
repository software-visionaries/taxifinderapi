package app.taxifinderapi.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String name;

    private String email;

    private String roles;

    private String password;

    public User() {
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = "ROLE_USER";
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @OneToMany(mappedBy = "user")
    private List<Address> addresses;
    @OneToMany(mappedBy = "user")
    private List<Question> questions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RefreshToken> refreshTokens;
    @OneToMany(mappedBy = "user")
    private List<Comment> comments;
    @OneToMany(mappedBy = "user")
    private List<ReplyComment> replyComments;
    @OneToMany(mappedBy = "user")
    private List<PushToken> pushTokens;
    @OneToOne(mappedBy = "user")
    private Rating rating;

    public List<ReplyComment> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<ReplyComment> replyComments) {
        this.replyComments = replyComments;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public List<ReplyComment> getReplayComments() {
        return replyComments;
    }

    public void setReplayComments(List<ReplyComment> replyComments) {
        this.replyComments = replyComments;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @OneToMany(mappedBy = "user")
    private List<Trip> trips = new ArrayList<>();

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public List<PushToken> getPushTokens() {
        return pushTokens;
    }

    public void setPushTokens(List<PushToken> pushTokens) {
        this.pushTokens = pushTokens;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
