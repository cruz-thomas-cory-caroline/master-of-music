package masterofmusic.masterofmusic.controllers.GameControllers;

import masterofmusic.masterofmusic.models.*;
import masterofmusic.masterofmusic.repositories.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.PresentationDirection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Controller
public class TriviaController {

    private final QuestionRepository questionDao;
    private final AnswerRepository answerDao;
    private final GameRepository gameDao;
    private final PlayerGameRepository playerGameDao;
    private final PlayerGameRoundRepository playerGameRoundDao;
    private final GenreRepository genreDao;

    public TriviaController(QuestionRepository questionDao, AnswerRepository answerDao, GameRepository gameDao, PlayerGameRepository playerGameDao, PlayerGameRoundRepository playerGameRoundDao, GenreRepository genreDao){
        this.questionDao = questionDao;
        this.answerDao = answerDao;
        this.gameDao = gameDao;
        this.playerGameDao = playerGameDao;
        this.playerGameRoundDao = playerGameRoundDao;
        this.genreDao = genreDao;
    }

    String difficultyOption;
    String genreOption;

    @PostMapping("/trivia-game")
    public String triviaGameSetup(
            @RequestParam(name = "difficultyOptions") String difficultySelection,
            @RequestParam(name = "genreOptions") String genreSelection)
     {
        difficultyOption = difficultySelection;
        genreOption = genreSelection;
        return "redirect:/trivia-game";
    }

    @GetMapping("trivia-game")
    public String viewTriviaGame(
            Model viewModel
    ) {
        ArrayList<Question> questionBank = questionDao.findAllByGameId(3L);
        ArrayList<Question> askedQuestionBank = new ArrayList<>();
        Random rand = new Random();
        Question question1 = questionBank.get(rand.nextInt(questionBank.size()));
        askedQuestionBank.add(question1);
        questionBank.removeAll(askedQuestionBank);
        Question question2 = questionBank.get(rand.nextInt(questionBank.size()));
        askedQuestionBank.add(question2);
        questionBank.removeAll(askedQuestionBank);
        Question question3 = questionBank.get(rand.nextInt(questionBank.size()));
        askedQuestionBank.add(question3);
        questionBank.removeAll(askedQuestionBank);
        viewModel.addAttribute("question1", question1);
        viewModel.addAttribute("question2", question2);
        viewModel.addAttribute("question3", question3);
        return "trivia-game";
    }

    @PostMapping("trivia-game/check")
    public String checkTriviaGame(
            @RequestParam(name = "solution1") String solution1,
            @RequestParam(name = "solution2") String solution2,
            @RequestParam(name = "solution3") String solution3
    ) {
        System.out.println(solution1 + ", " + solution2 + ", " + solution3);
        return "/index";
    }

}
