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

        ArrayList<PlayerGame> theoryGameList = playerGameDao.findAllByGameId(2L);

        int x = 0;//set variable to one

        for (PlayerGame theoryGame: theoryGameList) { //loop through theoryGameList
            if(theoryGame.getUser().getId() == user.getId()){ //the id of the database game equals the id of the current user then...
                x = 1; //set x to one
                break; //continue with program
            }
        }

        if(x != 1){ //if x does not equal one(the current users Id matched none in the database) then...
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


//      create a player game round
        PlayerGameRound playerGameRound = new PlayerGameRound();
        //tie this round to a game
        playerGameRound.setPlayerGame(playerGame);
        //set score to negative 1 so it is easier to find in postMapping
        playerGameRound.setScore(-1);


//       finding the user Id
        PlayerGame playerGameRedirect = playerGameDao.findByUserId(1);
        User userPlaying = playerGameRedirect.getUser();
        long userId = userPlaying.getId();
//         redirecting the user if there are no more questions
        if(id == 6){
            return "redirect:/profile/" + userId;
        }


//      pulling questions and answers
        ArrayList<Question> theoryList = questionDao.findAllByGameId(2L); // pull MT quest from database
        model.addAttribute("questions", theoryList.get(id).getQuestion()); //pass to Front end .get(id) is determined by path variable
        long questionId = theoryList.get(id).getId(); //return question object then get Id of that question
        model.addAttribute("answers", answerDao.getAllByQuestionId(questionId)); //get answers to that Q and pass to Front end
        return "/music-theory";
    }


    @PostMapping("/music-theory/{id}")
    public String submitAnswer(@RequestParam(name = "answers")String userAnswer, @PathVariable int id, Model model){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

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
            //save score to player game round
            PlayerGameRound playerGameRound = playerGameRoundDao.findByScore(-1); //find round
            playerGameRound.setScore(0); //reset score to zero
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
            PlayerGameRound playerGameRound = playerGameRoundDao.findByScore(-1); //find round
            playerGameRound.setScore(0); //reset score to zero
            PlayerGameRound dbPlayerGameRound = playerGameRoundDao.save(playerGameRound);
            model.addAttribute("wrong", "Sorry");
        }

        return "music-theory";
    }

}
