package masterofmusic.masterofmusic.controllers.GameControllers;

import masterofmusic.masterofmusic.models.Question;
import masterofmusic.masterofmusic.repositories.AnswerRepository;
import masterofmusic.masterofmusic.repositories.QuestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

@Controller
public class LyricsController {
    private final QuestionRepository questionDao;
    private final AnswerRepository answerDao;

    public LyricsController(QuestionRepository questionDao, AnswerRepository answerDao) {
        this.questionDao = questionDao;
        this.answerDao = answerDao;
    }

    @GetMapping("/finish-lyrics/{id}")
    public String viewQuizFormat(@PathVariable int id, Model model){
        ArrayList<Question> lyrics = questionDao.findAllByGameId(1L);
        model.addAttribute("questions", lyrics.get(id).getQuestion());
        long questionId = lyrics.get(id).getId();
        model.addAttribute("answers", answerDao.getAllByQuestionId(questionId));
        return "finish-lyrics";
    }
}
