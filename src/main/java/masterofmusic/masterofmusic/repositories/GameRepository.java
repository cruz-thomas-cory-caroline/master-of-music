package masterofmusic.masterofmusic.repositories;

import masterofmusic.masterofmusic.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    Game findById(long id);
}
