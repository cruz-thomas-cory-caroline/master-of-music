package masterofmusic.masterofmusic.controllers.GameControllers;

import masterofmusic.masterofmusic.repositories.AnswerRepository;
import masterofmusic.masterofmusic.repositories.QuestionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TriviaController {

    private final QuestionRepository questionDao;
    private final AnswerRepository answerDao;

    public TriviaController(QuestionRepository questionDao, AnswerRepository answerDao){
        this.questionDao = questionDao;
        this.answerDao = answerDao;
    }

//    @GetMapping("/trivia-game")
//    public String showTriviaGame(@RequestParam(name = "id") long id){
//        model.addAttribute("user", new User());
//        return "trivia-game";
//    }

//    @PostMapping("/sign-up")
//    public String saveUser(@ModelAttribute User user){
//        String hash = passwordEncoder.encode(user.getPassword());
//        user.setPassword(hash);
//        usersDao.save(user);
//        return "redirect:/login";
//    }
}
