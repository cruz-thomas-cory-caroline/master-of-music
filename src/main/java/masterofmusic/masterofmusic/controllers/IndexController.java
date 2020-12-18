package masterofmusic.masterofmusic.controllers;

import masterofmusic.masterofmusic.models.Game;
import masterofmusic.masterofmusic.models.Genre;
import masterofmusic.masterofmusic.models.PlayerGame;
import masterofmusic.masterofmusic.models.PlayerGameRound;
import masterofmusic.masterofmusic.repositories.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;

public class IndexController {

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


    public IndexController(QuestionRepository questionDao, AnswerRepository answerDao, GameRepository gameDao, PlayerGameRepository playerGameDao, PlayerGameRoundRepository playerGameRoundDao, GenreRepository genreDao){
        this.questionDao = questionDao;
        this.answerDao = answerDao;
        this.gameDao = gameDao;
        this.playerGameDao = playerGameDao;
        this.playerGameRoundDao = playerGameRoundDao;
        this.genreDao = genreDao;
    }

    @GetMapping("/index")
    public String indexPage(Model viewModel) {
        return "index";
    }

//    @PostMapping("/index")
//    public String gameSetup(
//            @RequestParam(name = "difficultyOptions") String difficultyOptions,
//            @RequestParam(name = "genreOptions") String genreOptions) {
//
//        Game game = gameDao.getOne(3L);
//        PlayerGame currentPlayerGame = new PlayerGame();
//        PlayerGameRound currentGameRound = new PlayerGameRound();
//        Genre currentGenre = new Genre();
//
//        currentPlayerGame.setGame(game);
//        currentPlayerGame.setScore(gameScore);
//        currentPlayerGame.setTimeElapsed(gameTime);
//
//        currentGameRound.setLevel(gameLevel);
//        currentGameRound.setPlay_time(play_time);
//        currentGameRound.setScore(roundScore);
//        currentGameRound.setPlayerGame(currentPlayerGame);
//        currentGameRound.setDifficulty(difficultyOptions);
//
//        currentGenre.setName(genreOptions);
//
//        playerGameDao.save(currentPlayerGame);
//        playerGameRoundDao.save(currentGameRound);
//        genreDao.save(currentGenre);
//        return "redirect:/trivia-game";
//    }
}
