package app.taxifinderapi.repository;

import app.taxifinderapi.model.Comment;
import app.taxifinderapi.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findCommentsByTrip(Trip trip);

}
