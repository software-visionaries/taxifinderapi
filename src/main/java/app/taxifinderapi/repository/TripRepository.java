package app.taxifinderapi.repository;

import app.taxifinderapi.model.Question;
import app.taxifinderapi.model.Trip;
import app.taxifinderapi.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByQuestion(Question question);
    List<Trip> findByUser(User user);


//    public Page<Trip> findAll (Pageable pageable);
}
