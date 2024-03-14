package app.taxifinderapi.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "user")
    private User user;
    @ManyToOne
    @JoinColumn(name = "town")
    private Town town;
    @ManyToOne
    @JoinColumn(name = "area")
    private Area area;
    @ManyToOne
    @JoinColumn(name = "section")
    private Section section;

}
