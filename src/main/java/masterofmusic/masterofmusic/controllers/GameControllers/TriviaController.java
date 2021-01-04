package masterofmusic.masterofmusic.controllers.GameControllers;

import masterofmusic.masterofmusic.models.*;
import masterofmusic.masterofmusic.repositories.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.PresentationDirection;
import javax.servlet.http.HttpServletRequest;
import java.sql.Array;
import java.sql.Timestamp;
import java.util.*;

@Controller
public class TriviaController {

    private final QuestionRepository questionDao;
    private final AnswerRepository answerDao;
    private final GameRepository gameDao;
    private final PlayerGameRepository playerGameDao;
    private final PlayerGameRoundRepository playerGameRoundDao;
    private final GenreRepository genreDao;

    public TriviaController(QuestionRepository questionDao, AnswerRepository answerDao, GameRepository gameDao, PlayerGameRepository playerGameDao, PlayerGameRoundRepository playerGameRoundDao, GenreRepository genreDao) {
        this.questionDao = questionDao;
        this.answerDao = answerDao;
        this.gameDao = gameDao;
        this.playerGameDao = playerGameDao;
        this.playerGameRoundDao = playerGameRoundDao;
        this.genreDao = genreDao;
    }

    String difficultyOption;
    String genreOption;
    PlayerGame currentPlayerGame = new PlayerGame();
    int totalScore = 0;
    Genre currentGenre = new Genre();
    Timestamp gameTime = new Timestamp(0);
    int gameLevel = 0;
    String play_time = "";


    @PostMapping("/trivia-game/3")
    public String triviaGameSetup(
            @RequestParam(name = "difficultyOptions") String difficultySelection,
            @RequestParam(name = "genreOptions") String genreSelection
    ) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        difficultyOption = difficultySelection;
        genreOption = genreSelection;
        Game game = gameDao.getOne(3L);
        currentPlayerGame.setUser(user);
        currentPlayerGame.setGame(game);
        currentPlayerGame.setScore(totalScore);
        currentPlayerGame.setTimeElapsed(gameTime);
        playerGameDao.save(currentPlayerGame);
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
                }
            }

            ArrayList<Question> randomQs = new ArrayList<>();
            for (var i = 0; i < 5; i++) {
                Question randRockQ = rockQuestions.get(rand.nextInt(rockQuestions.size()));
                randomQs.add(randRockQ);
                rockQuestions.remove(randRockQ);
            }
            viewModel.addAttribute("difficultyOption", difficultyOption);
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
        ArrayList<String> correctAnswers = new ArrayList<>();
        ArrayList<String> incorrectAnswers = new ArrayList<>();
        ArrayList<Question> correctQs = new ArrayList<>();
        ArrayList<Long> submittedAnswersIds = new ArrayList<>();
        ArrayList<String> submittedAnswers = new ArrayList<>();
        ArrayList<Question> incorrectQs = new ArrayList<>();
        ArrayList<Long> checkSubAnsForNull = new ArrayList<>();

        for (String questionId : questionIds) {
            String subAns = request.getParameter("question_" + questionId);
            if (subAns == null) {
                subAns = "-1";
                checkSubAnsForNull.add(Long.parseLong(subAns));
            } else {
                checkSubAnsForNull.add(Long.parseLong(subAns));
            }
        }

        int roundScore = 0;
        for (String questionId : questionIds) {
                long answerIsCorrect = questionDao.findAnswerIdCorrect(Long.parseLong(questionId));

                if (answerIsCorrect == checkSubAnsForNull.get(questionIds.indexOf(questionId))) {
                    correctAnswers.add(answerDao.getOne(answerIsCorrect).getAnswer());
                    correctQs.add(questionDao.getOne(Long.parseLong(questionId)));
                    roundScore += 100;
                } else if (checkSubAnsForNull.get(questionIds.indexOf(questionId)) == -1) {
                        submittedAnswersIds.add(checkSubAnsForNull.get(questionIds.indexOf(questionId)));
                        incorrectAnswers.add(answerDao.getOne(answerIsCorrect).getAnswer());
                        incorrectQs.add(questionDao.getOne(Long.parseLong(questionId)));
                } else if (answerIsCorrect != checkSubAnsForNull.get(questionIds.indexOf(questionId))) {
                    submittedAnswersIds.add(checkSubAnsForNull.get(questionIds.indexOf(questionId)));
                    incorrectAnswers.add(answerDao.getOne(answerIsCorrect).getAnswer());
                    incorrectQs.add(questionDao.getOne(Long.parseLong(questionId)));
                }
            }

        System.out.println(checkSubAnsForNull);

        for (Long submittedAnswer : submittedAnswersIds) {
            if (submittedAnswer != -1) {
                submittedAnswers.add(answerDao.getOne(submittedAnswer).getAnswer());
            } else {
                submittedAnswers.add("Nothing Submitted");
            }
        }

        gameLevel += 1;
        totalScore += roundScore;
        PlayerGameRound currentGameRound = new PlayerGameRound();
        currentGameRound.setLevel(gameLevel);
        currentGameRound.setPlay_time(play_time);
        currentGameRound.setScore(roundScore);
        currentGameRound.setPlayerGame(currentPlayerGame);
        currentPlayerGame.setScore(totalScore);
        playerGameDao.save(currentPlayerGame);
        currentGameRound.setDifficulty(difficultyOption);
        playerGameRoundDao.save(currentGameRound);


        System.out.println(incorrectAnswers);
        System.out.println(correctAnswers);
        model.addAttribute("submittedAnswers", submittedAnswers);
        model.addAttribute("correctQs", correctQs);
        model.addAttribute("incorrectQs", incorrectQs);
        model.addAttribute("correctAnswers", correctAnswers);
        model.addAttribute("incorrectAnswers", incorrectAnswers);
        model.addAttribute("roundsScoreTotal", totalScore);
        model.addAttribute("roundScore", roundScore);
        return "result";
    }

    @PostMapping("trivia-game/new")
    public String submit() {
        currentPlayerGame = new PlayerGame();
        totalScore = 0;
        return "redirect:/index";
    }


}
