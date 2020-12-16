package masterofmusic.masterofmusic.repositories;

import masterofmusic.masterofmusic.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

}
