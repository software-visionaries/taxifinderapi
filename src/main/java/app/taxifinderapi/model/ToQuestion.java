package app.taxifinderapi.model;

import jakarta.persistence.*;

@Entity
public class ToQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne()
    @JoinColumn(name = "address")
    private Address address;
}
