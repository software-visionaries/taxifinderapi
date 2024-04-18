package app.taxifinderapi.repository;

import app.taxifinderapi.model.Question;
import app.taxifinderapi.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {
//    Question findByTrip(Trip trip);
}
