package app.taxifinderapi.repository;

import app.taxifinderapi.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import app.taxifinderapi.model.Address;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByAddresses(List<Address> addresses);
}
