package masterofmusic.masterofmusic.Controllers;

import masterofmusic.masterofmusic.repositories.StatsRepository;
import masterofmusic.masterofmusic.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProfileController {

    private final UserRepository profileDao;
    private final StatsRepository statsDao;

    public ProfileController(UserRepository profileDao, StatsRepository statsDao){
        this.profileDao = profileDao;
        this.statsDao = statsDao;
    }

    @GetMapping("/profile/{id}")
    public String viewProfile(@PathVariable long id, Model model){
        model.addAttribute("profile", profileDao.getOne(id));
        model.addAttribute("stats", statsDao.findAllByUserId(id));
        return "/profile";
    }



}
