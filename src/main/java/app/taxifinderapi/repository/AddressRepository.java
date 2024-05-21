package app.taxifinderapi.repository;

import app.taxifinderapi.model.Address;
import app.taxifinderapi.model.Town;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByTown(Town town);
}
