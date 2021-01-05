package masterofmusic.masterofmusic.controllers;

import masterofmusic.masterofmusic.repositories.StatsRepository;
import masterofmusic.masterofmusic.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProfileController {

    private final UserRepository userDao;
    private final StatsRepository statsDao;

    public ProfileController(UserRepository userDao, StatsRepository statsDao){
        this.userDao = userDao;
        this.statsDao = statsDao;
    }

    @GetMapping("/profile/{id}")
    public String viewProfile(@PathVariable long id, Model model){
        model.addAttribute("user", userDao.getOne(id));

        return "profile";
    }



}
