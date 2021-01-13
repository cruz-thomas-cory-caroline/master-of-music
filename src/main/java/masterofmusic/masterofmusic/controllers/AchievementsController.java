package masterofmusic.masterofmusic.controllers;

import masterofmusic.masterofmusic.models.Achievement;
import masterofmusic.masterofmusic.models.PlayerGame;
import masterofmusic.masterofmusic.models.PlayerGameRound;
import masterofmusic.masterofmusic.models.User;
import masterofmusic.masterofmusic.repositories.AchievementRepository;
import masterofmusic.masterofmusic.repositories.PlayerGameRepository;
import masterofmusic.masterofmusic.repositories.PlayerGameRoundRepository;
import masterofmusic.masterofmusic.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AchievementsController {

    private final UserRepository userDao;
    private final PlayerGameRepository playerGameDao;
    private final PlayerGameRoundRepository playerGameRoundDao;
    private final AchievementRepository achievementDao;


    public AchievementsController(UserRepository userDao, PlayerGameRepository playerGameDao, PlayerGameRoundRepository playerGameRoundDao, AchievementRepository achievementDao) {
        this.userDao = userDao;
        this.playerGameDao = playerGameDao;
        this.playerGameRoundDao = playerGameRoundDao;
        this.achievementDao = achievementDao;
    }

    @GetMapping("/achievements")
    public String showAchievements(Model model) {

        if (!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User thisUser = userDao.getOne(user.getId());
            List<Achievement> userAchievements = thisUser.getUsers_achievements();
            if (userAchievements == null) {
                userAchievements = new ArrayList<>();
            }
            model.addAttribute("loggedIn", true);
            model.addAttribute("userAchievements", userAchievements);
            model.addAttribute("user", thisUser);

        }

        List<Achievement> allAchievements = achievementDao.findAll();

        model.addAttribute("allAchievements", allAchievements);
        return "achievements";
    }

}
