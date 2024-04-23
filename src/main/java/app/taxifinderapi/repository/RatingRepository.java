package app.taxifinderapi.repository;

import app.taxifinderapi.model.Rating;
import app.taxifinderapi.model.Trip;
import app.taxifinderapi.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating,Long> {
    Optional<Rating> findByUserAndTrip(Trip trip, User user);
    List<Rating> findAllByTrip(Trip trip);
    Rating findByUser( User user);
//    Page<Rating> findAllTrip(Pageable pageable);
}
