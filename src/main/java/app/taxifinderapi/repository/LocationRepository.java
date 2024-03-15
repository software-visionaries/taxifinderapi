package app.taxifinderapi.repository;

import app.taxifinderapi.model.Location;
import app.taxifinderapi.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findByTrip(Trip trip);
}
