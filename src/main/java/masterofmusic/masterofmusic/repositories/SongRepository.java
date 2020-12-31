package masterofmusic.masterofmusic.repositories;

import masterofmusic.masterofmusic.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface SongRepository extends JpaRepository <Song, Long> {
//    public interface SongRepository extends CrudRepository<Song, Long> {
//    ArrayList<Song> findAllByGameId(long id);
//    List<Song> findLyricsBySong(Game game);

}
