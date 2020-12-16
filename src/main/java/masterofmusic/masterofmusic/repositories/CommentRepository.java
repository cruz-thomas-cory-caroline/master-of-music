package masterofmusic.masterofmusic.repositories;

import masterofmusic.masterofmusic.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
