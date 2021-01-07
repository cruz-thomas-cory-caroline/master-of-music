package masterofmusic.masterofmusic.repositories;

import masterofmusic.masterofmusic.models.Genre;
import masterofmusic.masterofmusic.models.PlayerGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre findByName (String name);
}
