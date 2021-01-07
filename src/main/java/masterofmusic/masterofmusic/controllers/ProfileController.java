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

        ArrayList<PlayerGame> playerGamesForUser = playerGameDao.findAllByUserId(user.getId());
        ArrayList<PlayerGame> playerGamesForTrivia = new ArrayList<>();
        ArrayList<PlayerGame> playerGamesForLyric = new ArrayList<>();
        ArrayList<PlayerGame> playerGamesForTheory = new ArrayList<>();
        ArrayList<PlayerGame> playerGamesForUnscramble = new ArrayList<>();

        long totalTriviaScore = 0;
        long totalLyricScore = 0;
        long totalTheoryScore = 0;
        long totalUnscrambleScore = 0;


        for (PlayerGame playerGame : playerGamesForUser) {
            if (playerGame.getGame().getId() == 3) {
                totalTriviaScore += playerGame.getScore();
                playerGamesForTrivia.add(playerGame);
            } else if (playerGame.getGame().getId() == 2) {
                totalTheoryScore += playerGame.getScore();
                playerGamesForTheory.add(playerGame);
            } else if (playerGame.getGame().getId() == 1) {
                totalLyricScore += playerGame.getScore();
                playerGamesForLyric.add(playerGame);
            } else if (playerGame.getGame().getId() == 4) {
                totalUnscrambleScore += playerGame.getScore();
                playerGamesForUnscramble.add(playerGame);
            }
        }


        model.addAttribute("user", userDao.getOne(user.getId()));
        model.addAttribute("totalTriviaScore", totalTriviaScore);
        model.addAttribute("playerGamesForTrivia", playerGamesForTrivia);
        model.addAttribute("totalLyricScore", totalLyricScore);
        model.addAttribute("playerGamesForLyric", playerGamesForLyric);
        model.addAttribute("totalTheoryScore", totalTheoryScore);
        model.addAttribute("playerGamesForTheory", playerGamesForTheory);
        model.addAttribute("totalUnscrambleScore", totalUnscrambleScore);
        model.addAttribute("playerGamesForUnscramble", playerGamesForUnscramble);
        return "profile";
    }

    @PostMapping("/profile/avatar/{id}")
    public String changeAvatar(
            @PathVariable long id,
            @RequestParam(name = "avatarSelection") String avatarSelected,
            Model model
    ) {
//        User user = userDao.findById(id);
        User user = userDao.getOne(id);
        user.setImages(avatarSelected);
        User dbUser = userDao.save(user);
        System.out.println(avatarSelected);
        model.addAttribute("user", userDao.getOne(dbUser.getId()));
        return "redirect:/profile";
    }


}
