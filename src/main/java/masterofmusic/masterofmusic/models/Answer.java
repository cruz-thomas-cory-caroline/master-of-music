package masterofmusic.masterofmusic.models;

import org.aspectj.weaver.patterns.TypePatternQuestions;
import javax.persistence.*;
@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //auto generated id

    @Column(nullable = false)
    private boolean isCorrect; //boolean that determines if answer is true/false

   @ManyToOne
   @JoinColumn(name = "question_id")
    private Question question; //foreign key to questions table

    @Column(nullable = false)
    private String answer; //String that stores submitted answer

     public Answer(long id, boolean isCorrect, Question question, String answer) {
        this.id = id;
        this.isCorrect = isCorrect;
        this.question = question;
        this.answer = answer;
}

    public Answer(boolean isCorrect, Question question, String answer) {
        this.isCorrect = isCorrect;
        this.question = question;
        this.answer = answer;
    }

    public Answer() {

    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
