package masterofmusic.masterofmusic.repositories;

import masterofmusic.masterofmusic.models.LyricAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface LyricAnswerRepository extends JpaRepository<LyricAnswer, Long> {
    ArrayList<LyricAnswer> getAllBySongId(long id);
    List<LyricAnswer> findAllBySongId(long id);
}
