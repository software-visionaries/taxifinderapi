package app.taxifinderapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.taxifinderapi.model.PushToken;

@Repository
public interface PushTokenRepository extends JpaRepository<PushToken, Long> {

}
