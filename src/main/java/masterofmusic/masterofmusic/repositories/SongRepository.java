package masterofmusic.masterofmusic.repositories;

import masterofmusic.masterofmusic.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface SongRepository extends JpaRepository <Song, Long> {
    ArrayList<Song> findAllByGameId(long id);
    List<Song> findLyricsBySong(Game game);

    public default long findLyricAnswerIdCorrect(long songId) {
        Song song = findById(Long.parseLong(String.valueOf(songId))).get();
        for (LyricAnswer lyricAnswer : song.getLyricAnswers()) {
            if(lyricAnswer.isCorrect()) {
                return lyricAnswer.getId();
            }
        }
        return -1;
    }
}
