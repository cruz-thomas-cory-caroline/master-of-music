package masterofmusic.masterofmusic.repositories;

import masterofmusic.masterofmusic.models.Game;
import masterofmusic.masterofmusic.models.Question;
import masterofmusic.masterofmusic.models.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface SongRepository extends JpaRepository <Song, Long> {
//    ArrayList<Song> findAllByGameId(long id);
//    List<Song> findQuestionsBySongEquals(Game game);
}
