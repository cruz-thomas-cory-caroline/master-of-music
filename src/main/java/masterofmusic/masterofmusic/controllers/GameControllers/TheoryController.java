package masterofmusic.masterofmusic.Controllers.GameControllers;

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

    public TheoryController(QuestionRepository questionDao, AnswerRepository answerDao, PlayerGameRepository playerGameDao,PlayerGameRoundRepository playerGameRoundDao, GameRepository gameDao){
        this.questionDao = questionDao;
        this.answerDao = answerDao;
        this.playerGameDao = playerGameDao;
        this.playerGameRoundDao = playerGameRoundDao;
        this.gameDao = gameDao;
    }

    PlayerGame playerGame = new PlayerGame();


    @GetMapping("/music-theory/{id}")
    public String viewQuizFormat(
             @PathVariable int id,
             @RequestParam(name ="theoryDifficultyOptions" )String difficultySelection,
             Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Game game = gameDao.findById(2);
        long playerGameId = playerGame.getId();

        System.out.println("game ID: " + playerGameId);


        //TIMER
        model.addAttribute("songDifficulty", difficultySelection);


        //CREATE PLAYER GAME
        if(id == 0){
            //tie user id to playerGame
            playerGame.setUser(user);
            //tie game id to playerGame
            playerGame.setGame(game);
            //save player game to database
            playerGame.setScore(0);
            Date date = new Date();
            playerGame.setTimeElapsed(new Timestamp(date.getTime()));
            PlayerGame dbPlayerGame = playerGameDao.save(playerGame);
        }


        //REDIRECT TO PROFILE
        if(id == 6){
            return "redirect:/round-report/" + user.getId();
        }


//      PULLING QUESTIONS AND ANSWERS
        ArrayList<Question> theoryList = questionDao.findAllByGameId(2L); // pull MT quest from database
        model.addAttribute("questions", theoryList.get(id).getQuestion()); //pass to Front end .get(id) is determined by path variable
        long questionId = theoryList.get(id).getId(); //return question object then get Id of that question
        model.addAttribute("answers", answerDao.getAllByQuestionId(questionId)); //get answers to that Q and pass to Front end
        return "/music-theory";
    }

    @GetMapping("/round-report/{id}")
    public String reportScore(@PathVariable int id, Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long playerGameId = playerGame.getId();
        
        PlayerGame currentGame = playerGameDao.findById(playerGameId);
        long score = currentGame.getScore();

        model.addAttribute("score", score);
        return "round-report";
    }


    @PostMapping("/music-theory/{id}")
    public String submitAnswer(
            @RequestParam(name = "answers")String userAnswer,
            @RequestParam(name = "theoryDifficultyOptions") String difficultySelection,
            @PathVariable int id, Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ArrayList<PlayerGame> theoryGameList = playerGameDao.findAllByGameId(2L);


//        finding the correct answer
        ArrayList<Question> theoryList = questionDao.findAllByGameId(2L);
        long questionId = theoryList.get(id).getId();
        ArrayList<Answer> answerList = answerDao.getAllByQuestionId(questionId);
        String correctAnswer = "";
        for (Answer answer: answerList){
            if(answer.isCorrect()) {
                correctAnswer = answer.getAnswer();
            }
        }
//        comparing user answer to correct answer/good ending
        if(userAnswer.equalsIgnoreCase(correctAnswer) && difficultySelection.equalsIgnoreCase("option1")){
            correctAnswer(10,"easy");
            System.out.println(difficultySelection);
            model.addAttribute("correct", "Great Job!");
        }else if(userAnswer.equalsIgnoreCase(correctAnswer) && difficultySelection.equalsIgnoreCase("option2")){
            correctAnswer(45,"medium");
            System.out.println(difficultySelection);
            model.addAttribute("correct", "Great Job!");
        }else if(userAnswer.equalsIgnoreCase(correctAnswer) && difficultySelection.equalsIgnoreCase("option3")){
                correctAnswer(100,"hard");
                System.out.println(difficultySelection);
                model.addAttribute("correct", "Great Job!");
        }

        model.addAttribute("songDifficulty", difficultySelection);



//        comparing user answer to correct answer/bad ending
        if(!userAnswer.equalsIgnoreCase(correctAnswer)){
            model.addAttribute("wrong", "Sorry");
        }

        return "music-theory";
    }

    public void correctAnswer(int score, String difficulty){
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
