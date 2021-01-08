package masterofmusic.masterofmusic.controllers;

import masterofmusic.masterofmusic.models.*;
import masterofmusic.masterofmusic.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;

@Controller
public class IndexController {

    private final QuestionRepository questionDao;
    private final AnswerRepository answerDao;
    private final GameRepository gameDao;
    private final PlayerGameRepository playerGameDao;
    private final PlayerGameRoundRepository playerGameRoundDao;
    private final GenreRepository genreDao;


    public IndexController(QuestionRepository questionDao, AnswerRepository answerDao, GameRepository gameDao, PlayerGameRepository playerGameDao, PlayerGameRoundRepository playerGameRoundDao, GenreRepository genreDao){
        this.questionDao = questionDao;
        this.answerDao = answerDao;
        this.gameDao = gameDao;
        this.playerGameDao = playerGameDao;
        this.playerGameRoundDao = playerGameRoundDao;
        this.genreDao = genreDao;
    }

    @GetMapping("/index")
    public String indexPage(Model model) {
        boolean loggedIn = false;
        if(!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")){
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            loggedIn = true;
            model.addAttribute("user", user.getImages());
        }else{

            model.addAttribute("genericPfp","../../resources/static/img/unnamed.png" );
        }
        return "index";
    }

}

