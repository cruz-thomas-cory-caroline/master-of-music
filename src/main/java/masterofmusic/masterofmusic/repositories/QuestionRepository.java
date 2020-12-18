package masterofmusic.masterofmusic.repositories;


import masterofmusic.masterofmusic.models.Game;
import masterofmusic.masterofmusic.models.Post;
import masterofmusic.masterofmusic.models.Question;
import masterofmusic.masterofmusic.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
      ArrayList<Question> findAllByGameId(long id);
      List<Question> findQuestionsByGameEquals(Game game);
}
