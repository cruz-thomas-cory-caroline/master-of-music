package masterofmusic.masterofmusic.controllers.GameControllers;

import masterofmusic.masterofmusic.models.Game;
import masterofmusic.masterofmusic.models.Question;
import masterofmusic.masterofmusic.repositories.AnswerRepository;
import masterofmusic.masterofmusic.repositories.GameRepository;
import masterofmusic.masterofmusic.repositories.QuestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class TriviaController {

    private final QuestionRepository questionDao;
    private final AnswerRepository answerDao;
    private final GameRepository gameDao;

    public TriviaController(QuestionRepository questionDao, AnswerRepository answerDao, GameRepository gameDao){
        this.questionDao = questionDao;
        this.answerDao = answerDao;
        this.gameDao = gameDao;
    }

    @PostMapping("/trivia-game")
    public String userTriviaSelection(
            @RequestParam(name = "difficultyOptions") String difficultyOptions,
            @RequestParam(name = "genreOptions") String genreOptions
    ) {
//        Game game = gameDao.getOne(3L);

        return "trivia-game";
    }

    @GetMapping("/trivia-game")
    public String startTriviaGame(Model model) {
        List<Question> triviaQuestions = questionDao.getQuestionByGame_Id(3L);
        model.addAttribute("questions", triviaQuestions);
        long questionId = triviaQuestions.get(1).getId();
        model.addAttribute("answers", answerDao.getAllByQuestionId(questionId));
        return "trivia-game";
    }
}
