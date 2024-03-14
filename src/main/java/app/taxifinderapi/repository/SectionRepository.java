package app.taxifinderapi.repository;

import app.taxifinderapi.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends JpaRepository<Section,Long> {
    Section findByName(String name);
}
