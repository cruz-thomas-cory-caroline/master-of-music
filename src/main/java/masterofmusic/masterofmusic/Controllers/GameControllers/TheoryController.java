package masterofmusic.masterofmusic.Controllers.GameControllers;


import masterofmusic.masterofmusic.models.Question;
import masterofmusic.masterofmusic.repositories.QuestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;


@Controller
public class TheoryController {

    private final QuestionRepository questionDao;

    public TheoryController(QuestionRepository questionDao){
        this.questionDao = questionDao;
    }

    @GetMapping("/music-theory/{id}")
    public String viewQuizFormat(@PathVariable int id, Model model){
        ArrayList<Question> theoryList = questionDao.findAllByGameId(2L);
        model.addAttribute("questions", theoryList.get(id).getQuestion());
        return "/music-theory";
    }

}
