package app.taxifinderapi.repository;

import app.taxifinderapi.model.Comment;
import app.taxifinderapi.model.ReplyComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyCommentRepository extends JpaRepository<ReplyComment,Long> {
    List<ReplyComment> findReplyCommentByComment(Comment comment);
    List<ReplyComment> findByComment(Comment comment);
}
