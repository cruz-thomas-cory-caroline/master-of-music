package masterofmusic.masterofmusic.repositories;


import masterofmusic.masterofmusic.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
      List<Question> findAllByGameId(long id);
      List<Question> getQuestionByGame_Id(long id);
}
