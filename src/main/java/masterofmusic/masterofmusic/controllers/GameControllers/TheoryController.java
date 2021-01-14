package masterofmusic.masterofmusic.controllers.GameControllers;

import masterofmusic.masterofmusic.models.*;

import masterofmusic.masterofmusic.repositories.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;


@Controller
public class TheoryController {

    private final QuestionRepository questionDao;
    private final AnswerRepository answerDao;
    private final PlayerGameRepository playerGameDao;
    private final PlayerGameRoundRepository playerGameRoundDao;
    private final GameRepository gameDao;
    private final AchievementRepository achievementDao;
    private final UserRepository userDao;

    public TheoryController(QuestionRepository questionDao, AnswerRepository answerDao, PlayerGameRepository playerGameDao, PlayerGameRoundRepository playerGameRoundDao, GameRepository gameDao, AchievementRepository achievementDao, UserRepository userDao) {
        this.questionDao = questionDao;
        this.answerDao = answerDao;
        this.playerGameDao = playerGameDao;
        this.playerGameRoundDao = playerGameRoundDao;
        this.gameDao = gameDao;
        this.achievementDao = achievementDao;
        this.userDao = userDao;
    }




    PlayerGame playerGame = new PlayerGame();
    @GetMapping("/music-theory/{id}")
    public String viewQuizFormat(
            @PathVariable int id,
            @RequestParam(name = "theoryDifficultyOptions") String difficultySelection,
            Model model) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Game game = gameDao.findById(2);
        long playerGameId = playerGame.getId();

        System.out.println("game ID: " + playerGameId);


        //TIMER
        model.addAttribute("songDifficulty", difficultySelection);


        //CREATE PLAYER GAME
        if (id == 0) {
            //tie user id to playerGame
            if (user == null) {
                return "redirect:/login";
            } else {
                playerGame.setUser(user);
            }
            //tie game id to playerGame
            playerGame.setGame(game);
            //save player game to database
            playerGame.setScore(0);
            Date date = new Date();
            playerGame.setTimeElapsed(new Timestamp(date.getTime()));
            PlayerGame dbPlayerGame = playerGameDao.save(playerGame);
        }


        //REDIRECT TO PROFILE
        if (id == 6) {
            return "redirect:/round-report/" + user.getId();
        }

//      PROFILE PIC
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")) {
            User userToShow = userDao.getOne(user.getId());
            model.addAttribute("user", userToShow);
        }


//      PULLING QUESTIONS AND ANSWERS
        ArrayList<Question> theoryList = questionDao.findAllByGameId(2L); // pull MT quest from database
        model.addAttribute("questions", theoryList.get(id).getQuestion()); //pass to Front end .get(id) is determined by path variable
        long questionId = theoryList.get(id).getId(); //return question object then get Id of that question
        model.addAttribute("answers", answerDao.getAllByQuestionId(questionId)); //get answers to that Q and pass to Front end
        return "music-theory";
    }

    @GetMapping("/round-report/{id}")
    public String reportScore(@PathVariable int id, Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long playerGameId = playerGame.getId();

        PlayerGame currentGame = playerGameDao.findById(playerGameId);
        long score = currentGame.getScore();

//      PROFILE PIC
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")) {
            User userToShow = userDao.getOne(user.getId());
            model.addAttribute("user", userToShow);
        }

        model.addAttribute("score", score);
        return "round-report";
    }

    @PostMapping("/round-report/end")
    public String submit() {
        playerGame = new PlayerGame();

        return "redirect:/index";
    }


    @PostMapping("/music-theory/{id}")
    public String submitAnswer(
            @RequestParam(name = "answers") String userAnswer,
            @RequestParam(name = "theoryDifficultyOptions") String difficultySelection,
            @PathVariable int id, Model model) {
        User userIdToGrab = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDao.getOne(userIdToGrab.getId());
        ArrayList<PlayerGame> theoryGameList = playerGameDao.findAllByGameId(2L);

        //PROFILE PIC
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")) {
            User userToShow = userDao.getOne(user.getId());
            model.addAttribute("user", userToShow);
        }

//        finding the correct answer
        ArrayList<Question> theoryList = questionDao.findAllByGameId(2L);
        long questionId = theoryList.get(id).getId();
        ArrayList<Answer> answerList = answerDao.getAllByQuestionId(questionId);
        String correctAnswer = "";
        for (Answer answer : answerList) {
            if (answer.isCorrect()) {
                correctAnswer = answer.getAnswer();
            }
        }
//        comparing user answer to correct answer/good ending
        if (userAnswer.equalsIgnoreCase(correctAnswer) && difficultySelection.equalsIgnoreCase("option1")) {
            correctAnswer(10, "easy");
            System.out.println(difficultySelection);
            model.addAttribute("correct", "Great Job!");
            model.addAttribute("score", 10);
        } else if (userAnswer.equalsIgnoreCase(correctAnswer) && difficultySelection.equalsIgnoreCase("option2")) {
            correctAnswer(45, "medium");
            System.out.println(difficultySelection);
            model.addAttribute("correct", "Great Job!");
            model.addAttribute("score", 45);
        } else if (userAnswer.equalsIgnoreCase(correctAnswer) && difficultySelection.equalsIgnoreCase("option3")) {
            correctAnswer(100, "hard");
            System.out.println(difficultySelection);
            model.addAttribute("correct", "Great Job!");
            model.addAttribute("score", 100);
        }

        model.addAttribute("songDifficulty", difficultySelection);


//        comparing user answer to correct answer/bad ending
        if (!userAnswer.equalsIgnoreCase(correctAnswer)) {
            model.addAttribute("wrong", "Sorry");
        }


        //ACHIEVEMENTS
        List<Achievement> gameAchievements = achievementDao.findAllByGameId(2);
        List<Achievement> userAchievements = user.getUsers_achievements();

        long playerGameId = playerGame.getId();
        PlayerGame currentGame = playerGameDao.findById(playerGameId);
        long finalScore = currentGame.getScore();

        if (userAchievements == null) {
            System.out.println("Nothing has been Earned");
            userAchievements = new ArrayList<>();
        } else {
            for (Achievement ach : userAchievements) {
                System.out.println("You've earned: " + ach.getName());
            }
        }

        boolean awardEarned = false;
        boolean bombAwardEarned = false;
        boolean smartAwardEarned = false;
        boolean scroogeaAwardEarned = false;
        boolean mediocreAwardEarned = false;

        List<Achievement> newAwards = new ArrayList<>();



        if(userAchievements.contains(gameAchievements.get(0))){
            System.out.println("Already have it: Da Bomb");
        }else{
            if (finalScore >= 600 && !userAchievements.contains(gameAchievements.get(0))) {
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

                    bombAwardEarned = true;
                    newAwards.add(achToChange);
                }
            }
        }


        if(userAchievements.contains(gameAchievements.get(1))){
            System.out.println("Already have it: Smarty Pants");
        }else{
            if (finalScore == 60 && !userAchievements.contains(gameAchievements.get(1))) {
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

                    smartAwardEarned = true;
                    newAwards.add(achToChange);
                }
            }
        }


        if(userAchievements.contains(gameAchievements.get(1))){
            System.out.println("Already have it: Smarty Pants");
        }else{
            if (finalScore == 270 && !userAchievements.contains(gameAchievements.get(1))) {
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

                    smartAwardEarned = true;
                    newAwards.add(achToChange);
                }
            }
        }


        List<PlayerGame> gameScores = playerGameDao.findAllByUserId(user.getId());

        int overallScore = 0;
        for (PlayerGame score : gameScores) {
            overallScore += score.getScore();
        }

        if(userAchievements.contains(gameAchievements.get(2))){
            System.out.println("Already have it: The Scrooge");
        }else{
            if (overallScore >= 1000 && !userAchievements.contains(gameAchievements.get(2))) {
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

                    scroogeaAwardEarned = true;
                    newAwards.add(achToChange);
                }
            }
        }


        if(userAchievements.contains(gameAchievements.get(3))){
            System.out.println("Aleady have it:  Mediocre Master");
        }else{
            if (finalScore == 135 && !userAchievements.contains(gameAchievements.get(3))) {
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

                    mediocreAwardEarned = true;
                    newAwards.add(achToChange);
                }
            }
        }

        if(mediocreAwardEarned || scroogeaAwardEarned || smartAwardEarned || bombAwardEarned){
            awardEarned = true;
        }

        model.addAttribute("awardEarned", awardEarned);
        model.addAttribute("newAwards", newAwards);
        return "music-theory";
    }


    public void correctAnswer(int score, String difficulty) {
        PlayerGameRound playerGameRound = new PlayerGameRound();
        playerGameRound.setPlayerGame(playerGame);
        playerGameRound.setScore(playerGameRound.getScore() + score); //increment
        playerGameRound.setDifficulty(difficulty);
        playerGameRound.setPlay_time("0");
        PlayerGameRound dbPlayerGameRound = playerGameRoundDao.save(playerGameRound);

        //save score to player game

        playerGame.setScore(playerGame.getScore() + score); //increment
        PlayerGame dbWinner = playerGameDao.save(playerGame); //save
    }

}
