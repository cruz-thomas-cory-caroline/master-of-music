package masterofmusic.masterofmusic.controllers;

import masterofmusic.masterofmusic.models.PlayerGame;
import masterofmusic.masterofmusic.models.User;
import masterofmusic.masterofmusic.repositories.StatsRepository;
import masterofmusic.masterofmusic.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

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
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("profile", profileDao.getOne(id));

        ArrayList<PlayerGame> userGames = statsDao.findAllByUserId(user.getId());
        long counter = 0;
        for (PlayerGame game: userGames) {
            long score = game.getScore();
            counter += score;
        }
        System.out.println(counter);

        model.addAttribute("stats", counter);
        return "/profile";
    }



}
