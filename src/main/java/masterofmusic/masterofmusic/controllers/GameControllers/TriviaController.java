package masterofmusic.masterofmusic.controllers.GameControllers;

import masterofmusic.masterofmusic.models.*;
import masterofmusic.masterofmusic.repositories.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    Timestamp gameTime = new Timestamp(0);
    int gameScore = 0;
    int gameLevel = 0;
    String play_time = "";
    int roundScore = 0;

    public TriviaController(QuestionRepository questionDao, AnswerRepository answerDao, GameRepository gameDao, PlayerGameRepository playerGameDao, PlayerGameRoundRepository playerGameRoundDao, GenreRepository genreDao){
        this.questionDao = questionDao;
        this.answerDao = answerDao;
        this.gameDao = gameDao;
        this.playerGameDao = playerGameDao;
        this.playerGameRoundDao = playerGameRoundDao;
        this.genreDao = genreDao;
    }

    @GetMapping("/trivia-game")
    public String viewTriviaGame(Model viewModel) {
        ArrayList<Question> questions = questionDao.findAllByGameId(3L);
        Random rand = new Random();
        Question question = questions.get(rand.nextInt(questions.size()));
        viewModel.addAttribute("question", question);
        return "trivia-game";
    }

    @PostMapping("/trivia-game")
    public String gameSetup(
            @RequestParam(name = "difficultyOptions") String difficultyOptions,
            @RequestParam(name = "genreOptions") String genreOptions) {

        Game game = gameDao.getOne(3L);
        PlayerGame currentPlayerGame = new PlayerGame();
        PlayerGameRound currentGameRound = new PlayerGameRound();
        Genre currentGenre = new Genre();

        currentPlayerGame.setGame(game);
        currentPlayerGame.setScore(gameScore);
        currentPlayerGame.setTimeElapsed(gameTime);

        currentGameRound.setLevel(gameLevel);
        currentGameRound.setPlay_time(play_time);
        currentGameRound.setScore(roundScore);
        currentGameRound.setPlayerGame(currentPlayerGame);
        currentGameRound.setDifficulty(difficultyOptions);

        currentGenre.setName(genreOptions);

        playerGameDao.save(currentPlayerGame);
        playerGameRoundDao.save(currentGameRound);
        genreDao.save(currentGenre);
        return "redirect:/trivia-game";
    }

    @PostMapping("/trivia-game/{id}")
    public String viewNextQuizQuestion(
            @PathVariable long id,
            @RequestParam (name = "solutions") String userAnswer,
            Model viewModel) {
        boolean isCorrect = false;
        Question question = questionDao.getOne(id);
        List<Answer> availableAnswers = question.getAnswers();
        for (Answer answer : availableAnswers) {
            if (answer.isCorrect() & answer.getAnswer() == userAnswer) {
                isCorrect = true;
                break;
            }
        }
//        viewModel.addAttribute("isCorrect", isCorrect);
        return "redirect:/trivia-game";
    }

    @GetMapping("/trivia-game/{id}")
    public String viewNewTriviaQuestion(
            @PathVariable long id,
            Model viewModel) {
        ArrayList<Question> questions = questionDao.findAllByGameId(3L);
        Random rand = new Random();
        ArrayList<Question> newQuestionList = new ArrayList<>();
        for (Question question : questions) {
            if (question.getId() == id) {
                continue;
            } else {
                newQuestionList.add(question);
            }
        }
        Question question = questions.get(rand.nextInt(newQuestionList.size()));
        viewModel.addAttribute("question", question);
        return "trivia-game/";
    }

}
