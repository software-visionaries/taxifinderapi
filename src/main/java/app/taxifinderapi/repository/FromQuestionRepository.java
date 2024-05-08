package app.taxifinderapi.repository;

import app.taxifinderapi.model.Address;
import app.taxifinderapi.model.FromQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FromQuestionRepository extends JpaRepository<FromQuestion, Long> {

    FromQuestion findByAddress(Address address);
}
