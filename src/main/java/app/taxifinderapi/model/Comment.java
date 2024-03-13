package app.taxifinderapi.model;


import jakarta.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comment_id;

    @Column(name = "message", length = 100000)
    private String message;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

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
