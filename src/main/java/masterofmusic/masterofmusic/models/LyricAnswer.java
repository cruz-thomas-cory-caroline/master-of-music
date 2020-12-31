package masterofmusic.masterofmusic.models;

import javax.persistence.*;

@Entity
@Table(name = "lyricAnswers")
public class LyricAnswer implements java.io.Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //auto generated id

    @Column(nullable = false)
    private boolean isCorrect;//boolean that determines if answer is true/false

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id")
    private Song song; //foreign key to song table

    @Column(nullable = false)
    private String lyricAnswer; //String that stores submitted answer

    public LyricAnswer() {
    }

    public LyricAnswer(long id, boolean isCorrect, Song song, String lyricAnswer) {
        this.id = id;
        this.isCorrect = isCorrect;
        this.song = song;
        this.lyricAnswer = lyricAnswer;
    }

    public LyricAnswer(boolean isCorrect, Song song, String lyricAnswer) {
        this.isCorrect = isCorrect;
        this.song = song;
        this.lyricAnswer = lyricAnswer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public String getLyricAnswer() {
        return lyricAnswer;
    }

    public void setLyricAnswer(String lyricAnswer) {
        this.lyricAnswer = lyricAnswer;
    }
}
