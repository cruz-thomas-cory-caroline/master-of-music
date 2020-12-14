package masterofmusic.masterofmusic.models;

import javax.persistence.*;

@Entity
@Table(name = "player_game_rounds")
public class PlayerGameRound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String play_time;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false)
    private int level;

    @Column(nullable = false, length = 45)
    private String difficulty;

    @ManyToOne
    @JoinColumn (name = "player_game_id")
    private PlayerGame playerGame;

    public PlayerGameRound() {
    }

    public PlayerGameRound(long id, String play_time, int score, int level, String difficulty, PlayerGame playerGame) {
        this.id = id;
        this.play_time = play_time;
        this.score = score;
        this.level = level;
        this.difficulty = difficulty;
        this.playerGame = playerGame;
    }

    public PlayerGameRound(String play_time, int score, int level, String difficulty, PlayerGame playerGame) {
        this.play_time = play_time;
        this.score = score;
        this.level = level;
        this.difficulty = difficulty;
        this.playerGame = playerGame;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlay_time() {
        return play_time;
    }

    public void setPlay_time(String play_time) {
        this.play_time = play_time;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public PlayerGame getPlayerGame() {
        return playerGame;
    }

    public void setPlayerGame(PlayerGame playerGame) {
        this.playerGame = playerGame;
    }
}
