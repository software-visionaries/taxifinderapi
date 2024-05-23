package app.taxifinderapi.repository;

import app.taxifinderapi.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
   Address findByUserAndTownAndAreaAndSection(User user, Town town, Area area, Section section);
   List<Address> findByTown(Town town);
}
