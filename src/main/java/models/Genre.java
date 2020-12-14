package models;

import javax.persistence.*;

@Entity
@Table(name = "game_genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 45)
    private String name;

    @OneToOne
    @JoinColumn (name = "player_game_round_id")
    private PlayerGameRound playerGameRound;

    public Genre() {
    }

    public Genre(long id, String name, PlayerGameRound playerGameRound) {
        this.id = id;
        this.name = name;
        this.playerGameRound = playerGameRound;
    }

    public Genre(String name, PlayerGameRound playerGameRound) {
        this.name = name;
        this.playerGameRound = playerGameRound;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PlayerGameRound getPlayerGameRound() {
        return playerGameRound;
    }

    public void setPlayerGameRound(PlayerGameRound playerGameRound) {
        this.playerGameRound = playerGameRound;
    }

}
