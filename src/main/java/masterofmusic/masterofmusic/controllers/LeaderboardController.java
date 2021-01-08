package masterofmusic.masterofmusic.controllers;

import masterofmusic.masterofmusic.models.Game;
import masterofmusic.masterofmusic.models.PlayerGame;
import masterofmusic.masterofmusic.models.User;
import masterofmusic.masterofmusic.repositories.GameRepository;
import masterofmusic.masterofmusic.repositories.PlayerGameRepository;
import masterofmusic.masterofmusic.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LeaderboardController {

    private final UserRepository userDao;
    private final GameRepository gameDao;

    public LeaderboardController(UserRepository userDao, GameRepository gameDao) {
        this.userDao = userDao;
        this.gameDao = gameDao;
    }

    @GetMapping("/leaderboard")
    public String getLeaderboard(Model model) {

        boolean loggedIn = false;

       if (!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")) {
           loggedIn = true;
       }

        List<User> rankedUserList = userDao.getRankedListOfUsersByOverallScore();
        List<Integer> rankedScores = userDao.getRankedListOfAllOverallScores();

        for (Game game : gameDao.findAll()) {
            List<User> rankedUsersByGame = userDao.getRankedListOfUsersByGameId(game.getId());
            model.addAttribute("game"+game.getId()+"Players", rankedUsersByGame);
            model.addAttribute("game"+game.getId()+"Scores", userDao.getRankedListOfUsersScoreByGameId(game.getId()));

            if (loggedIn) {
                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                User userToFind = userDao.getOne(user.getId());
                model.addAttribute("game"+game.getId()+"Standing", rankedUsersByGame.indexOf(userToFind)+1);
                System.out.println(rankedUserList.indexOf(user)+1);
                model.addAttribute("globalRank", (rankedUserList.indexOf(userToFind))+1);
                model.addAttribute("playerScore", rankedScores.get(rankedUserList.indexOf(userToFind)));
                model.addAttribute("loggedIn", true);
            }
        }

        model.addAttribute("rankedUsers", rankedUserList);
        model.addAttribute("rankedScores", rankedScores);
        return "leaderboard";
    }

}
