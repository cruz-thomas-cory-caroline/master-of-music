package masterofmusic.masterofmusic.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 45)
    private String name;

//    @OneToOne
//    @JoinColumn (name = "player_game_round_id")
//    private PlayerGameRound playerGameRound;

    @ManyToMany(mappedBy = "genres")
    private List<Song> songs;

    public Genre() {
    }

    public Genre(long id, String name,  List<Song> songs) {
        this.id = id;
        this.name = name;
//        this.playerGameRound = playerGameRound;
        this.songs = songs;
    }

    public Genre(String name,List<Song> songs) {
        this.name = name;
//        this.playerGameRound = playerGameRound;
        this.songs = songs;
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

//    public PlayerGameRound getPlayerGameRound() {
//        return playerGameRound;
//    }
//
//    public void setPlayerGameRound(PlayerGameRound playerGameRound) {
//        this.playerGameRound = playerGameRound;
//    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

}
