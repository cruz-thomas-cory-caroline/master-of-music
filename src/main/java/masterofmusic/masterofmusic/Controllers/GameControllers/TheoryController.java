package masterofmusic.masterofmusic.Controllers.GameControllers;


import masterofmusic.masterofmusic.models.Question;
import masterofmusic.masterofmusic.repositories.QuestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class TheoryController {

    private final QuestionRepository questionDao;

    public TheoryController(QuestionRepository questionDao){
        this.questionDao = questionDao;
    }

    @GetMapping("/music-theory")
    public String viewQuizFormat(Model model){
        List<Question> theoryList = questionDao.findAllByGameId(2L);
        for(int i = 0; i <= theoryList.size(); i++){

        }
        model.addAttribute("questions", questionDao.findAllByGameId(2L));
        return "/music-theory";
    }

}
