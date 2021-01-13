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
    private final UserRepository userDao;
    private final AchievementRepository achievementDao;

    public TriviaController(QuestionRepository questionDao, AnswerRepository answerDao, GameRepository gameDao, PlayerGameRepository playerGameDao, PlayerGameRoundRepository playerGameRoundDao, GenreRepository genreDao, UserRepository userDao, AchievementRepository achievementDao) {
        this.questionDao = questionDao;
        this.answerDao = answerDao;
        this.gameDao = gameDao;
        this.playerGameDao = playerGameDao;
        this.playerGameRoundDao = playerGameRoundDao;
        this.genreDao = genreDao;
        this.userDao = userDao;
        this.achievementDao = achievementDao;
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
        currentPlayerGame = new PlayerGame();
        currentPlayerGame.setUser(user);
        currentPlayerGame.setGame(game);
        currentPlayerGame.setScore(totalScore);
        currentPlayerGame.setTimeElapsed(gameTime);
        playerGameDao.save(currentPlayerGame);

        totalScore = 0;
        gameLevel = 0;
        return "redirect:/trivia-game";
    }

    @GetMapping("trivia-game")
    public String viewTriviaGame(
            Model viewModel
    ) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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

        viewModel.addAttribute("user", user);
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
        User user_db = userDao.findById(user.getId());
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

        gameLevel += 1;
        totalScore += roundScore;

        //  CHECK FOR ACHIEVEMENTS
        ArrayList<PlayerGame> playerGamesForUser = playerGameDao.findAllByUserId(user.getId());
        ArrayList<List<PlayerGameRound>> playerGameRoundsForTrivia = new ArrayList<>();

        var gameCount = 0;
        var scoreCount = 0;
        var streakCount = 0;
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
        for (List<PlayerGameRound> playerGameRound : playerGameRoundsForTrivia) {
            for (PlayerGameRound playerGameRound1 : playerGameRound) {
                if (playerGameRound1.getDifficulty().equals("easy") && playerGameRound1.getScore() == 500 || (roundScore == 500 && difficultyOption.equalsIgnoreCase("easy"))) {
                    easyPerfect = true;
                    break;
                }
            }
        }

        for (List<PlayerGameRound> playerGameRound : playerGameRoundsForTrivia) {
            for (PlayerGameRound playerGameRound1 : playerGameRound) {
                if (playerGameRound1.getDifficulty().equals("medium") && playerGameRound1.getScore() == 500 || (roundScore == 500 && difficultyOption.equalsIgnoreCase("medium"))) {
                    mediumPerfect = true;
                    break;
                }
            }
        }

        for (List<PlayerGameRound> playerGameRound : playerGameRoundsForTrivia) {
            for (PlayerGameRound playerGameRound1 : playerGameRound) {
                if (playerGameRound1.getDifficulty().equals("hard") && playerGameRound1.getScore() == 500 || (roundScore == 500 && difficultyOption.equalsIgnoreCase("hard"))) {
                    hardPerfect = true;
                    break;
                }
            }
        }

        boolean gemAwardEarned = false;
        boolean jediAwardEarned = false;
        boolean phoenixAwardEarned = false;
        boolean shieldAwardEarned = false;
        boolean triviaAwardEarned = false;

        List<Achievement> newAwards = new ArrayList<>();
        List<Achievement> userAchievements = user_db.getUsers_achievements();

        if (userAchievements == null) {
            userAchievements = new ArrayList<>();
        }

        List<Achievement> gameAchievements = achievementDao.findAllByGameId(3L);

//         CHECK FOR GEM - GOT A PERFECT SCORE IN ALL DIFFICULTIES (13)
        if ((easyPerfect && mediumPerfect && hardPerfect) && !userAchievements.contains(gameAchievements.get(0))) {
            Achievement achToChange = achievementDao.getOne(gameAchievements.get(0).getId());
            List<User> usersWhoHaveBadge = achToChange.getUsers();
            if (usersWhoHaveBadge == null) {
                usersWhoHaveBadge = new ArrayList<>();
            }
            if (!usersWhoHaveBadge.contains(userDao.getOne(user.getId()))) {
                usersWhoHaveBadge.add(user);
                achToChange.setUsers(usersWhoHaveBadge);
                achievementDao.save(achToChange);
                userAchievements.add(achToChange);
                User userToSave = userDao.getOne(user.getId());
                userToSave.setUsers_achievements(userAchievements);
                userDao.save(userToSave);
                gemAwardEarned = true;
                newAwards.add(achToChange);
            }
        }

//         CHECK FOR JEDI - PLAYED AT LEAST 10 GAMES (14)
        if (((gameCount) >= 10) && !userAchievements.contains(gameAchievements.get(1))) {
            Achievement achToChange = achievementDao.getOne(gameAchievements.get(1).getId());
            List<User> usersWhoHaveBadge = achToChange.getUsers();
            if (usersWhoHaveBadge == null) {
                usersWhoHaveBadge = new ArrayList<>();
            }
            if (!usersWhoHaveBadge.contains(userDao.getOne(user.getId()))) {
                usersWhoHaveBadge.add(user);
                achToChange.setUsers(usersWhoHaveBadge);
                achievementDao.save(achToChange);
                userAchievements.add(achToChange);
                User userToSave = userDao.getOne(user.getId());
                userToSave.setUsers_achievements(userAchievements);
                userDao.save(userToSave);
                jediAwardEarned = true;
                newAwards.add(achToChange);
            }
        }


        // CHECK FOR PHOENIX - TOTAL SCORE OF 5,000 (15)
        if ((scoreCount + roundScore >= 5000) && !userAchievements.contains(gameAchievements.get(2))) {
            Achievement achToChange = achievementDao.getOne(gameAchievements.get(2).getId());
            List<User> usersWhoHaveBadge = achToChange.getUsers();
            if (usersWhoHaveBadge == null) {
                usersWhoHaveBadge = new ArrayList<>();
            }
            if (!usersWhoHaveBadge.contains(userDao.getOne(user.getId()))) {
                usersWhoHaveBadge.add(user);
                achToChange.setUsers(usersWhoHaveBadge);
                achievementDao.save(achToChange);
                userAchievements.add(achToChange);
                User userToSave = userDao.getOne(user.getId());
                userToSave.setUsers_achievements(userAchievements);
                userDao.save(userToSave);
                phoenixAwardEarned = true;
                newAwards.add(achToChange);
            }
        }


//        CHECK FOR SHIELD - PERFECT SCORE STREAK ON 5 GAMES (16)
        if (roundScore == 500) {
            streakCount += 1;
            for (PlayerGame playerGame : playerGamesForUser) {
                if (playerGame.getGame().getId() == 3) {
                    if (playerGame.getScore() == 500) {
                        streakCount += 1;
                    }
                    if (playerGame.getScore() != 500) {
                        streakCount = 0;
                    }
                    if (streakCount >= 5) {
                        break;
                    }
                }
            }
        }

        if ((streakCount >= 5) && !userAchievements.contains(gameAchievements.get(3))) {
            Achievement achToChange = achievementDao.getOne(gameAchievements.get(3).getId());
            List<User> usersWhoHaveBadge = achToChange.getUsers();
            if (usersWhoHaveBadge == null) {
                usersWhoHaveBadge = new ArrayList<>();
            }
            if (!usersWhoHaveBadge.contains(userDao.getOne(user.getId()))) {
                usersWhoHaveBadge.add(user);
                achToChange.setUsers(usersWhoHaveBadge);
                achievementDao.save(achToChange);
                userAchievements.add(achToChange);
                User userToSave = userDao.getOne(user.getId());
                userToSave.setUsers_achievements(userAchievements);
                userDao.save(userToSave);
                shieldAwardEarned = true;
                newAwards.add(achToChange);
            }
        }

        if (gemAwardEarned || jediAwardEarned || phoenixAwardEarned || shieldAwardEarned) {
            triviaAwardEarned = true;
        }

        //UPDATE DATABASES
        PlayerGameRound currentGameRound = new PlayerGameRound();
        currentGameRound.setLevel(gameLevel);
        currentGameRound.setPlay_time(play_time);
        currentGameRound.setScore(roundScore);
        currentGameRound.setPlayerGame(currentPlayerGame);
        currentPlayerGame.setScore(totalScore);
        playerGameDao.save(currentPlayerGame);
        currentGameRound.setDifficulty(difficultyOption);
        playerGameRoundDao.save(currentGameRound);

        model.addAttribute("newAwards", newAwards);
        model.addAttribute("triviaAwardEarned", triviaAwardEarned);
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
