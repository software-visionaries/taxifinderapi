package app.taxifinderapi.repository;

import app.taxifinderapi.model.Address;
import app.taxifinderapi.model.ToQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToQuestionRepository extends JpaRepository<ToQuestion,Long> {
    ToQuestion findByAddress(Address address);
}
