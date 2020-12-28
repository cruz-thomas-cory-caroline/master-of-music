package masterofmusic.masterofmusic.repositories;


import masterofmusic.masterofmusic.models.PlayerGameRound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerGameRoundRepository extends JpaRepository<PlayerGameRound, Long> {
    PlayerGameRound findByPlayerGameId(long id);

}
