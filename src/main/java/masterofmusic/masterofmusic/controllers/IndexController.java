package masterofmusic.masterofmusic.controllers;

import masterofmusic.masterofmusic.models.Game;
import masterofmusic.masterofmusic.repositories.AnswerRepository;
import masterofmusic.masterofmusic.repositories.GameRepository;
import masterofmusic.masterofmusic.repositories.QuestionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class IndexController {

    private final QuestionRepository questionDao;
    private final AnswerRepository answerDao;
    private final GameRepository gameDao;


    public IndexController(QuestionRepository questionDao, AnswerRepository answerDao, GameRepository gameDao){
        this.questionDao = questionDao;
        this.answerDao = answerDao;
        this.gameDao = gameDao;
    }

//    @PostMapping("/index")
//    public String indexPage(
//            @RequestParam(name = "difficultyOptions") String difficultyOptions,
//            @RequestParam(name = "genreOptions") String genreOptions
//    ) {
//        Game game = gameDao.getOne(3L);
//
//        return "posts/index";
//    }

}
