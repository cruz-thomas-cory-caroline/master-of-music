package masterofmusic.masterofmusic.repositories;


import masterofmusic.masterofmusic.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
      ArrayList<Question> findAllByGameId(long id);
}
