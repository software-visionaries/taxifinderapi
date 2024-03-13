package app.taxifinderapi.repository;

import app.taxifinderapi.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {}
