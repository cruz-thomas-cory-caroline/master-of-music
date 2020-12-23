package masterofmusic.masterofmusic.controllers.GameControllers;

import masterofmusic.masterofmusic.models.*;
import masterofmusic.masterofmusic.repositories.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.PresentationDirection;
import javax.servlet.http.HttpServletRequest;
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
    boolean easyOption = false;
    long mediumOption = 10000;
    long hardOption = 2000;

    @PostMapping("/trivia-game/3")
    public String triviaGameSetup(
            @RequestParam(name = "difficultyOptions") String difficultySelection,
            @RequestParam(name = "genreOptions") String genreSelection,
            Model viewModel
    ) {
        difficultyOption = difficultySelection;
        genreOption = genreSelection;
        return "redirect:/trivia-game";
    }

    @GetMapping("trivia-game")
    public String viewTriviaGame(
            Model viewModel
    ) {
        System.out.println(difficultyOption);
        System.out.println(genreOption);
        Random rand = new Random();
        Genre genre = genreDao.getOne(1L);

        if (genreOption.equals("Rock")) {

            ArrayList<Question> questions = questionDao.findAllByGameId(3L);
            ArrayList<Question> rockQuestions = new ArrayList<>();

            for (Question question : questions) {
                if (question.getQuestion_genres().contains(genre)) {
                    rockQuestions.add(question);
                    System.out.println(question.getQuestion());
                }
            }

            ArrayList<Question> randomQs = new ArrayList<>();
            for (var i = 0; i < 5; i++) {
                randomQs.add(rockQuestions.get(rand.nextInt(rockQuestions.size())));
            }

            for (Question rq : randomQs) {
                System.out.println(rq.getQuestion());
            }
            System.out.println(questions);

            viewModel.addAttribute("questions", randomQs);
        }

        return "trivia-game";
    }

    @PostMapping("trivia-game/submit")
    public String submit(
            @RequestParam(name = "questionId") ArrayList<String> questionIds,
            HttpServletRequest request,
            Model model
    ) {
        int score = 0;
        for (String questionId : questionIds) {
            long answerIsCorrect = questionDao.findAnswerIdCorrect(Long.parseLong(questionId));
            if(answerIsCorrect == Long.parseLong(request.getParameter("question_"+questionId))) {
                score++;
            }
        }
        model.addAttribute("score", score);
        return "result";
    }

}
