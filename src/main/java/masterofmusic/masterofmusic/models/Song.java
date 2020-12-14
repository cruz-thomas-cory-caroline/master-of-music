package masterofmusic.masterofmusic.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "songs")
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String artist;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String lyrics;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "songs_genres",
            joinColumns = {@JoinColumn(name = "song_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")}
    )
    private List<Genre> genres;

    //CREATE
    public Song(long id, String title, String artist, String lyrics) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.lyrics = lyrics;
    }

    //READ
    public Song(String title, String artist, String lyrics) {
        this.title = title;
        this.artist = artist;
        this.lyrics = lyrics;
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
}
