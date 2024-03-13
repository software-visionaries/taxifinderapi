package app.taxifinderapi.model;

import jakarta.persistence.*;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "user")
    private User user;
    @OneToOne()
    @JoinColumn(name = "town")
    private Town town;
    @OneToOne()
    @JoinColumn(name = "area")
    private Area area;
    @OneToOne()
    @JoinColumn(name = "section")
    private Section section;
}
