package masterofmusic.masterofmusic.controllers;

import masterofmusic.masterofmusic.models.PlayerGame;
import masterofmusic.masterofmusic.models.PlayerGameRound;
import masterofmusic.masterofmusic.models.User;
import masterofmusic.masterofmusic.repositories.PlayerGameRepository;
import masterofmusic.masterofmusic.repositories.PlayerGameRoundRepository;
import masterofmusic.masterofmusic.repositories.UserRepository;
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


    public AchievementsController(UserRepository userDao, PlayerGameRepository playerGameDao, PlayerGameRoundRepository playerGameRoundDao) {
        this.userDao = userDao;
        this.playerGameDao = playerGameDao;
        this.playerGameRoundDao = playerGameRoundDao;
    }

    @GetMapping("/achievement/{id}")
    public String showBadges(
            @PathVariable long id,
            Model model
    ) {
        User user = userDao.findById(3L);

        return "achievements";
    }


}
