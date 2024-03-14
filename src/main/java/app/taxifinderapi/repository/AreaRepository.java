package app.taxifinderapi.repository;

import app.taxifinderapi.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {

    Area findByName(String name);
}
