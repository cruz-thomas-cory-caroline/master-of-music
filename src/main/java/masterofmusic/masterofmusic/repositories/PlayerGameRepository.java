package masterofmusic.masterofmusic.repositories;


import masterofmusic.masterofmusic.models.PlayerGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerGameRepository extends JpaRepository<PlayerGame, Long> {

}
