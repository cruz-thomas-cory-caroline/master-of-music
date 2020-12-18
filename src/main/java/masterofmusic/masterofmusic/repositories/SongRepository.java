package masterofmusic.masterofmusic.repositories;

import masterofmusic.masterofmusic.models.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface SongRepository extends JpaRepository <Song, Long> {

}
