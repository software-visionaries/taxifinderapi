package app.taxifinderapi.repository;

import app.taxifinderapi.model.Question;
import app.taxifinderapi.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByQuestion(Question question);
}
