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

    public TheoryController(QuestionRepository questionDao, AnswerRepository answerDao, PlayerGameRepository playerGameDao,PlayerGameRoundRepository playerGameRoundDao, GameRepository gameDao){
        this.questionDao = questionDao;
        this.answerDao = answerDao;
        this.playerGameDao = playerGameDao;
        this.playerGameRoundDao = playerGameRoundDao;
        this.gameDao = gameDao;
    }

    PlayerGame playerGame = new PlayerGame();

    @GetMapping("/music-theory/{id}")
    public String viewQuizFormat(@PathVariable int id, Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Game game = gameDao.findById(2);


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
            return "redirect:/profile/" + user.getId();
        }


//      PULLING QUESTIONS AND ANSWERS
        ArrayList<Question> theoryList = questionDao.findAllByGameId(2L); // pull MT quest from database
        model.addAttribute("questions", theoryList.get(id).getQuestion()); //pass to Front end .get(id) is determined by path variable
        long questionId = theoryList.get(id).getId(); //return question object then get Id of that question
        model.addAttribute("answers", answerDao.getAllByQuestionId(questionId)); //get answers to that Q and pass to Front end
        return "/music-theory";
    }


    @PostMapping("/music-theory/{id}")
    public String submitAnswer(@RequestParam(name = "answers")String userAnswer, @PathVariable int id, Model model){
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
        if(userAnswer.equalsIgnoreCase(correctAnswer)){
            PlayerGameRound playerGameRound = new PlayerGameRound();
            playerGameRound.setPlayerGame(playerGame);
            playerGameRound.setScore(playerGameRound.getScore() + 2); //increment
            playerGameRound.setDifficulty("easy");
            playerGameRound.setPlay_time("0");
            PlayerGameRound dbPlayerGameRound = playerGameRoundDao.save(playerGameRound);
            model.addAttribute("correct", "Great Job!");

            //save score to player game

            playerGame.setScore(playerGame.getScore() + 2); //increment
            PlayerGame dbWinner = playerGameDao.save(playerGame); //save
        }

//        comparing user answer to correct answer/bad ending
        if(!userAnswer.equalsIgnoreCase(correctAnswer)){
//            PlayerGameRound playerGameRound = playerGameRoundDao.findByPlayerGameId(playerGameDao.findByGameIdAndUserId(2L, user.getId()).getId());// see line 105
//            playerGameRound.setScore(0); //reset score to zero
//            PlayerGameRound dbPlayerGameRound = playerGameRoundDao.save(playerGameRound);
            model.addAttribute("wrong", "Sorry");
        }

        return "music-theory";
    }

}
