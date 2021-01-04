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

//    Timestamp gameTime = new Timestamp(0);
//    int gameScore = 0;
//    int gameLevel = 0;
//    String play_time = "";
//    int roundScore = 0;


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
//        Game game = gameDao.getOne(3L);
//        viewModel.addAttribute("game", game);
        return "index";
    }

}
