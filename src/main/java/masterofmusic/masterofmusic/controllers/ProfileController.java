package masterofmusic.masterofmusic.controllers;

import masterofmusic.masterofmusic.models.PlayerGame;
import masterofmusic.masterofmusic.models.User;
import masterofmusic.masterofmusic.repositories.PlayerGameRepository;
import masterofmusic.masterofmusic.repositories.StatsRepository;
import masterofmusic.masterofmusic.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class ProfileController {

    private final UserRepository userDao;
    private final PlayerGameRepository playerGameDao;

    public ProfileController(UserRepository userDao, PlayerGameRepository playerGameDao){
        this.userDao = userDao;
        this.playerGameDao = playerGameDao;
    }

    @GetMapping("/profile")
    public String viewProfile(
            Model model
    ){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ArrayList<PlayerGame> playerGamesForTrivia = playerGameDao.findAllByGame_Id(3L);
        long totalTriviaScore = 0;

        for (PlayerGame playerGame : playerGamesForTrivia) {
            if (playerGame.getUser().getId() == user.getId()) {
                totalTriviaScore += playerGame.getScore();
            }
        }

        System.out.println("totalTriviaScore = " + totalTriviaScore);

        model.addAttribute("user", userDao.getOne(user.getId()));
        model.addAttribute("totalTriviaScore", totalTriviaScore);
        model.addAttribute("playerGamesForTrivia", playerGamesForTrivia);
        return "profile";
    }

    @PostMapping("/profile/avatar/{id}")
    public String changeAvatar(
            @PathVariable long id,
            @RequestParam(name = "avatarSelection") String avatarSelected,
            Model model
    ) {
        User user = userDao.findById(id);
        user.setImages(avatarSelected);
        User dbUser = userDao.save(user);
        System.out.println(avatarSelected);
        model.addAttribute("user", dbUser);
        return "profile";
    }




}
