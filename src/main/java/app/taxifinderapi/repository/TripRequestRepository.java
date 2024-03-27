package app.taxifinderapi.repository;

import app.taxifinderapi.model.TripRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface TripRequestRepository extends JpaRepository<TripRequest, Long> {
    @Query(value = "SELECT * FROM trip_request WHERE from_town = :fromTown AND to_town = :toTown AND from_area = :fromArea AND to_area = :toArea AND from_section = :fromSection AND to_section = :toSection", nativeQuery = true)
    List<TripRequest> findFilteredTrips(String fromTown, String toTown, String fromArea, String toArea, String fromSection, String toSection);
}
