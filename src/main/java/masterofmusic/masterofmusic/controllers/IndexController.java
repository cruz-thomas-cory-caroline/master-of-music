package masterofmusic.masterofmusic.controllers;

import masterofmusic.masterofmusic.models.Game;
import masterofmusic.masterofmusic.repositories.AnswerRepository;
import masterofmusic.masterofmusic.repositories.GameRepository;
import masterofmusic.masterofmusic.repositories.QuestionRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class IndexController {

    private final QuestionRepository questionDao;
    private final AnswerRepository answerDao;
    private final GameRepository gameDao;


    public IndexController(QuestionRepository questionDao, AnswerRepository answerDao, GameRepository gameDao){
        this.questionDao = questionDao;
        this.answerDao = answerDao;
        this.gameDao = gameDao;
    }

    @GetMapping("/index")
    public String indexPage(Model viewModel) {
        return "index";
    }

}
