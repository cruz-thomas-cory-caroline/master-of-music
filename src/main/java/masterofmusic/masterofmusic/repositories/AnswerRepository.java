package masterofmusic.masterofmusic.repositories;


import masterofmusic.masterofmusic.models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    ArrayList<Answer> getAllByQuestionId(long id);
    List<Answer> findAllByQuestionId(long id);
}
