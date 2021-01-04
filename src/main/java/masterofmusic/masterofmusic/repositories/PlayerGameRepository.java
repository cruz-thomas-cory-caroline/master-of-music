package masterofmusic.masterofmusic.repositories;


import masterofmusic.masterofmusic.models.PlayerGame;
import masterofmusic.masterofmusic.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PlayerGameRepository extends JpaRepository<PlayerGame, Long> {
    PlayerGame findByUserId(long id);
    PlayerGame findById(long id);
    ArrayList<PlayerGame> findAllByGameId(long id);

}
