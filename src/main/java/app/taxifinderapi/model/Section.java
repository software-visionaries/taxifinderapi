package app.taxifinderapi.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne()
    @JoinColumn(name = "area")
    private Area area;

}
