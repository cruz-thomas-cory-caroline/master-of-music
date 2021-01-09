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

    @GetMapping("/achievement/{id}")
    public String showBadges(
            @PathVariable long id,
            Model model
    ) {
        User user = userDao.findById(3L);

        ArrayList<PlayerGame> playerGamesForUser = playerGameDao.findAllByUserId(user.getId());
        ArrayList<List<PlayerGameRound>> playerGameRoundsForTrivia = new ArrayList<>();
        boolean triviaGemAward;
        boolean easyPerfect = false;
        boolean mediumPerfect = false;
        boolean hardPerfect = false;

        for (PlayerGame playerGame : playerGamesForUser) {
            if (playerGame.getGame().getId() == 3) {
                playerGameRoundsForTrivia.add(playerGame.getPlayerGameRounds());
            }
        }

        for (List<PlayerGameRound> playerGameRound : playerGameRoundsForTrivia) {
            for (PlayerGameRound playerGameRound1 : playerGameRound) {
                if (playerGameRound1.getDifficulty().equals("easy") && playerGameRound1.getScore() == 500) {
                    easyPerfect = true;
                    if (playerGameRound1.getDifficulty().equals("medium") && playerGameRound1.getScore() == 500) {
                        mediumPerfect = true;
                        if (playerGameRound1.getDifficulty().equals("hard") && playerGameRound1.getScore() == 500) {
                            hardPerfect = true;
                        }
                    }
                }
            }
        }

        System.out.println(easyPerfect);
        System.out.println(mediumPerfect);
        System.out.println(hardPerfect);

        return "achievements";
    }

    @GetMapping("/achievements")
    public String showAchievements(Model model) {

        if (!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<Achievement> userAchievements = user.getUsers_achievements();
            if (userAchievements == null) {
                userAchievements = new ArrayList<>();
            }
            model.addAttribute("loggedIn", true);
            model.addAttribute("userAchievements", userAchievements);
        }

        List<Achievement> allAchievements = achievementDao.findAll();
        model.addAttribute("allAchievements", allAchievements);

        return "achievementsV2";


    }


}
