package masterofmusic.masterofmusic.models;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "player_games")
public class PlayerGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private Timestamp timeElapsed;

    @Column(nullable = false)
    private int score;

    @OneToOne
    private Game game;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playerGame")
    private List<PlayerGameRound> playerGameRounds;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

    public PlayerGame() {
    }

    public PlayerGame(long id, Timestamp timeElapsed, List<PlayerGameRound> playerGameRounds, User user) {
        this.id = id;
        this.timeElapsed = timeElapsed;
        this.playerGameRounds = playerGameRounds;
        this.user = user;
    }

    public PlayerGame(Timestamp timeElapsed, List<PlayerGameRound> playerGameRounds, User user) {
        this.timeElapsed = timeElapsed;
        this.playerGameRounds = playerGameRounds;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(Timestamp timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<PlayerGameRound> getPlayerGameRounds() {
        return playerGameRounds;
    }

    public void setPlayerGameRound(List<PlayerGameRound> playerGameRounds) {
        this.playerGameRounds = playerGameRounds;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
