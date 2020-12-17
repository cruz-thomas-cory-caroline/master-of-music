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

    @OneToOne
    @JoinColumn (name = "game_id")
    private Game game;

    @ManyToMany(mappedBy = "song_genres")
    private List<Song> songs;

    @ManyToMany(mappedBy = "question_genres")
    private List<Question> questions;

    public Genre() {
    }

    public Genre(long id, String name,  List<Song> songs, Game game) {
        this.id = id;
        this.name = name;
        this.game = game;
        this.songs = songs;
    }

    public Genre(String name,List<Song> songs, Game game) {
        this.name = name;
        this.game = game;
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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

}
