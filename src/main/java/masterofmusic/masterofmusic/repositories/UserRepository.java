package masterofmusic.masterofmusic.repositories;




import masterofmusic.masterofmusic.models.PlayerGame;
import masterofmusic.masterofmusic.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.isAdmin = true")
    User findByisAdmin(User user);

    @Query("SELECT c FROM User c WHERE c.email = ?1")
    public User findByEmail(String email);
    public User findByResetPasswordToken(String token);
    User findById(long id);

    @Query("select sum(pg.score) from PlayerGame pg where pg.user.id = ?1")
    public int getOverallScoreByUserId(long id);

    @Query("select sum(pg.score) from PlayerGame pg group by pg.user.id order by sum(pg.score) desc")
    public List<Integer> getRankedListOfAllOverallScores();

    @Query("select pg.user from PlayerGame pg group by pg.user.id order by sum(pg.score) desc")
    public List<User> getRankedListOfUsersByOverallScore();

    @Query("select pg.user from PlayerGame pg where pg.game.id = ?1 group by pg.user.id order by sum(pg.score) desc")
    public List<User> getRankedListOfUsersByGameId(long id);

    @Query("select sum(pg.score) from PlayerGame pg where pg.game.id = ?1 group by pg.user.id order by sum(pg.score) desc")
    public List<Integer> getRankedListOfUsersScoreByGameId(long id);


}

