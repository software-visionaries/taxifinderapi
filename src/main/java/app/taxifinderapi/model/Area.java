package app.taxifinderapi.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "area")
    private List<Section> sections;
    @ManyToOne()
    @JoinColumn(name = "town")
    private Town town;

}
