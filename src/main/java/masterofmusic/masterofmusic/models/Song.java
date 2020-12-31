package masterofmusic.masterofmusic.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "songs")
public class Song implements java.io.Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = true)
    private Game game;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "song")
    private Set<LyricAnswer> lyricAnswers = new HashSet<>(0);

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String artist;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String lyrics;

    @Column(nullable = true, length = 255, unique = true)
    private String song;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "songs_genres",
            joinColumns = {@JoinColumn(name = "song_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")}
    )
    private List<Genre> song_genres;

    //CREATE
    public Song(long id, String title, String artist, String lyrics, Game game, Set<LyricAnswer> lyricAnswers, String song) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.lyrics = lyrics;
        this.game = game;
        this.lyricAnswers = lyricAnswers;
        this.song = song;
    }

    //READ
    public Song(String title, String artist, String lyrics, Game game, Set<LyricAnswer> lyricAnswers, String song) {
        this.title = title;
        this.artist = artist;
        this.lyrics = lyrics;
        this.game = game;
        this.lyricAnswers = lyricAnswers;
        this.song = song;
    }

    public Song() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Genre> getSong_genres() {
        return song_genres;
    }

    public void setSong_genres(List<Genre> song_genres) {
        this.song_genres = song_genres;
    }

    public Set<LyricAnswer> getLyricAnswers() {
        return lyricAnswers;
    }

    public void setLyricAnswers(Set<LyricAnswer> lyricAnswers) {
        this.lyricAnswers = lyricAnswers;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
