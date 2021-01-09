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
        Random rand = new Random();
        Genre genre = genreDao.findByName(genreOption.toLowerCase());
        ArrayList<Question> questions = questionDao.findAllByGameId(3L);

        if (genreOption.equals("Rock")) {
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
            viewModel.addAttribute("questions", randomQs);

        } else if (genreOption.equals("Pop")) {
            ArrayList<Question> popQuestions = new ArrayList<>();
            for (Question question : questions) {
                if (question.getQuestion_genres().contains(genre)) {
                    popQuestions.add(question);
                }
            }
            ArrayList<Question> randomQs = new ArrayList<>();
            for (var i = 0; i < 5; i++) {
                Question randPopQ = popQuestions.get(rand.nextInt(popQuestions.size()));
                randomQs.add(randPopQ);
                popQuestions.remove(randPopQ);
            }
            viewModel.addAttribute("questions", randomQs);
        } else if (genreOption.equals("Hip-Hop")) {
            ArrayList<Question> hipHopQuestions = new ArrayList<>();
            for (Question question : questions) {
                if (question.getQuestion_genres().contains(genre)) {
                    hipHopQuestions.add(question);
                }
            }
            ArrayList<Question> randomQs = new ArrayList<>();
            for (var i = 0; i < 5; i++) {
                Question randHipHopQ = hipHopQuestions.get(rand.nextInt(hipHopQuestions.size()));
                randomQs.add(randHipHopQ);
                hipHopQuestions.remove(randHipHopQ);
            }
            viewModel.addAttribute("questions", randomQs);
        } else if (genreOption.equals("Country")) {
            ArrayList<Question> countryQuestions = new ArrayList<>();
            for (Question question : questions) {
                if (question.getQuestion_genres().contains(genre)) {
                    countryQuestions.add(question);
                }
            }
            ArrayList<Question> randomQs = new ArrayList<>();
            for (var i = 0; i < 5; i++) {
                Question randCountryQ = countryQuestions.get(rand.nextInt(countryQuestions.size()));
                randomQs.add(randCountryQ);
                countryQuestions.remove(randCountryQ);
            }
            viewModel.addAttribute("questions", randomQs);
        }

        viewModel.addAttribute("difficultyOption", difficultyOption);
        return "trivia-game";
    }

    @PostMapping("trivia-game/submit")
    public String submit(
            @RequestParam(name = "questionId") ArrayList<String> questionIds,
            HttpServletRequest request,
            Model model
    ) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ArrayList<String> correctAnswers = new ArrayList<>();
        ArrayList<String> incorrectAnswers = new ArrayList<>();
        ArrayList<Question> correctQs = new ArrayList<>();
        ArrayList<Long> submittedAnswersIds = new ArrayList<>();
        ArrayList<String> submittedAnswers = new ArrayList<>();
        ArrayList<Question> incorrectQs = new ArrayList<>();
        ArrayList<Long> checkSubAnsForNull = new ArrayList<>();

//        CHECK FOR NULL ANSWERS
        for (String questionId : questionIds) {
            String subAns = request.getParameter("question_" + questionId);
            if (subAns == null) {
                subAns = "-1";
                checkSubAnsForNull.add(Long.parseLong(subAns));
            } else {
                checkSubAnsForNull.add(Long.parseLong(subAns));
            }
        }

//        CHECK FOR CORRECT AND INCORRECT ANSWERS
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

        //  CHECK FOR ACHIEVEMENTS
        ArrayList<PlayerGame> playerGamesForUser = playerGameDao.findAllByUserId(user.getId());
        ArrayList<List<PlayerGameRound>> playerGameRoundsForTrivia = new ArrayList<>();
        var gameCount = 0;
        var scoreCount = 0;
        boolean triviaGemAward;
        boolean easyPerfect = false;
        boolean mediumPerfect = false;
        boolean hardPerfect = false;

        for (PlayerGame playerGame : playerGamesForUser) {
            if (playerGame.getGame().getId() == 3) {
                playerGameRoundsForTrivia.add(playerGame.getPlayerGameRounds());
                gameCount += 1;
                scoreCount += playerGame.getScore();
            }
        }

        // CHECK FOR GEM - PERFECT SCORE IN ALL DIFFICULTIES (13)
//        for (List<PlayerGameRound> playerGameRound : playerGameRoundsForTrivia) {
//            for (PlayerGameRound playerGameRound1 : playerGameRound) {
//                if (playerGameRound1.getDifficulty().equals("easy") && playerGameRound1.getScore() == 500) {
//                    easyPerfect = true;
//                    if (playerGameRound1.getDifficulty().equals("medium") && playerGameRound1.getScore() == 500) {
//                        mediumPerfect = true;
//                        if (playerGameRound1.getDifficulty().equals("hard") && playerGameRound1.getScore() == 500) {
//                            hardPerfect = true;
//                        }
//                    }
//                }
//            }
//        }
//
//        if (easyPerfect && mediumPerfect && hardPerfect) {
//
//        }

        // CHECK FOR JEDI - PLAYED AT LEAST 10 GAMES (14)
//        if (gameCount == 10) {
//
//        }

        // CHECK FOR PHOENIX - TOTAL SCORE OF 5,000 (15)
//        if (scoreCount == 5000) {
//
//        }

        // CHECK FOR SHIELD - PERFECT SCORE STREAK ON 5 GAMES (16)
        for (PlayerGame playerGame : playerGamesForUser) {
            if (playerGame.getGame().getId() == 3) {
                playerGameRoundsForTrivia.add(playerGame.getPlayerGameRounds());
                gameCount += 1;
                scoreCount += playerGame.getScore();
            }
        }


//        System.out.println(easyPerfect);
//        System.out.println(mediumPerfect);
//        System.out.println(hardPerfect);

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
        User user_db = currentPlayerGame.getUser();

        model.addAttribute("submittedAnswers", submittedAnswers);
        model.addAttribute("correctQs", correctQs);
        model.addAttribute("incorrectQs", incorrectQs);
        model.addAttribute("correctAnswers", correctAnswers);
        model.addAttribute("incorrectAnswers", incorrectAnswers);
        model.addAttribute("roundsScoreTotal", totalScore);
        model.addAttribute("roundScore", roundScore);
        model.addAttribute("user", user_db);
        return "result";
    }

    @PostMapping("trivia-game/new")
    public String submit() {
        currentPlayerGame = new PlayerGame();
        totalScore = 0;
        gameLevel = 0;
        return "redirect:/index";
    }


}
