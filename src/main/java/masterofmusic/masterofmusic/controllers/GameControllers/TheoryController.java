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

    @GetMapping("/music-theory/{id}")
    public String viewQuizFormat(@PathVariable int id, Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Game game = gameDao.findById(2);
        PlayerGame playerGame = new PlayerGame();


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
//       finding the user Id
        PlayerGame playerGameRedirect = playerGameDao.findByUserId(user.getId());
        User userPlaying = playerGameRedirect.getUser();
        long userId = userPlaying.getId();
//         redirecting the user if there are no more questions
        if(id == 6){
            return "redirect:/profile/" + userId;
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
            playerGameRound.setPlayerGame(playerGameDao.findByGameIdAndUserId(2L, user.getId()));

            playerGameRound.setScore(playerGameRound.getScore() + 2); //increment
            PlayerGameRound dbPlayerGameRound = playerGameRoundDao.save(playerGameRound);
            model.addAttribute("correct", "Great Job!");
            //save score to player game
            PlayerGame winner = playerGameDao.findByUserId(user.getId()); //find game by userId
            winner.setScore(winner.getScore() + 2); //increment
            PlayerGame dbWinner = playerGameDao.save(winner); //save
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
