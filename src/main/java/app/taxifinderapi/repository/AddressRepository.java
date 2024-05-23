package app.taxifinderapi.repository;

import app.taxifinderapi.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
   Address findByUserAndTownAndAreaAndSection(User user, Town town, Area area, Section section);
}
