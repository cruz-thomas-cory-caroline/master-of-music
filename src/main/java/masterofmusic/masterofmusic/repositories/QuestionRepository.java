package masterofmusic.masterofmusic.repositories;


import masterofmusic.masterofmusic.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
      ArrayList<Question> findAllByGameId(long id);

      public default long findAnswerIdCorrect(long questionId) {
            Question question = findById(Long.parseLong(String.valueOf(questionId))).get();
            for (Answer answer : question.getAnswers()) {
                  if(answer.isCorrect()) {
                        return answer.getId();
                  }
            }
            return -1;
      }
}
