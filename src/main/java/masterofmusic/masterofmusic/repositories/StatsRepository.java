package masterofmusic.masterofmusic.repositories;


import masterofmusic.masterofmusic.models.PlayerGame;
import masterofmusic.masterofmusic.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface StatsRepository extends JpaRepository<PlayerGame, Long> {
//        PlayerGame findAllByUserId(long id);
        ArrayList<PlayerGame> findAllByUserId(long id);
}
