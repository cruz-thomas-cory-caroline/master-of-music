package masterofmusic.masterofmusic.models;

import com.mysql.cj.jdbc.Blob;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //auto generated id

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers; //error will go away when game table is created

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "questions_genres",
            joinColumns = {@JoinColumn(name = "question_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")}
    )
    private List<Genre> question_genres;

    @Column(nullable = true)
    private String img; //String for image url for image game

    @Column(nullable = true, length = 255, unique = true)
    private String question; //string for question on question games

    @Column(nullable = true)
    private String sound; //Url for sound byte. Is sound stored with url's?

     public Question(long id, List<Answer> answers, String img, String question, String sound, Game game) {
        this.id = id;
        this.answers = answers;
        this.img = img;
        this.question = question;
        this.sound = sound;
        this.game = game;
}
    public Question(List<Answer> answers, String img, String question, String sound, Game game) {
        this.answers = answers;
        this.img = img;
        this.question = question;
        this.sound = sound;
        this.game = game;
    }

    public Question() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Genre> getQuestion_genres() {
        return question_genres;
    }

    public void setQuestion_genres(List<Genre> question_genres) {
        this.question_genres = question_genres;
    }
}
