package app.taxifinderapi.model;

import jakarta.persistence.*;

@Entity
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
    @ManyToOne()
    @JoinColumn(name = "user")
    private User user;

}
