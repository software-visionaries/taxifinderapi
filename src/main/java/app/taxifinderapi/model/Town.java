package app.taxifinderapi.model;

import jakarta.persistence.*;

import java.util.List;
@Entity
public class Town {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "town")
    private List<Area> areas;
}
