package masterofmusic.masterofmusic.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, length = 50)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private List<Question> questions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private List<Song> songs;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "game")
    private List<Achievement> achievements;


    public Game() {
    }

    public Game(String name) {
        this.name = name;
    }

    public Game(long id, String name) {
        this.id = id;
        this.name = name;
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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<Achievement> achievements) {
        this.achievements = achievements;
    }
}
