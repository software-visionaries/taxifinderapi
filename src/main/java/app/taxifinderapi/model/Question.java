package app.taxifinderapi.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "questionId")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @OneToOne
    @JoinColumn(name = "fromQuestion")
    private FromQuestion fromQuestion;
    
    @OneToOne
    @JoinColumn(name = "toQuestion")
    private ToQuestion toQuestion;

    @OneToMany(mappedBy = "question")
    private List<Trip> trips;
    
    @ManyToOne()
    @JoinColumn(name = "user")
    private User user;

    public Question() {
    }

    public Question(FromQuestion fromQuestion, ToQuestion toQuestion) {
        this.fromQuestion = fromQuestion;
        this.toQuestion = toQuestion;
    }

    public List<Trip> getTrips() {
        return trips;
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public FromQuestion getFromQuestion() {
        return fromQuestion;
    }

    public void setFromQuestion(FromQuestion fromQuestion) {
        this.fromQuestion = fromQuestion;
    }

    public ToQuestion getToQuestion() {
        return toQuestion;
    }

    public void setToQuestion(ToQuestion toQuestion) {
        this.toQuestion = toQuestion;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
