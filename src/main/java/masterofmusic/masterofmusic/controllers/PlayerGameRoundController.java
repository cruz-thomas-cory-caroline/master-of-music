package masterofmusic.masterofmusic.controllers;


import masterofmusic.masterofmusic.models.PlayerGameRound;
import masterofmusic.masterofmusic.models.User;
import masterofmusic.masterofmusic.repositories.PlayerGameRepository;
import masterofmusic.masterofmusic.repositories.PlayerGameRoundRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PlayerGameRoundController {

    private final PlayerGameRoundRepository playerGameRoundDao;
    private final PlayerGameRepository playerGameDao;

    public PlayerGameRoundController(PlayerGameRoundRepository playerGameRoundDao, PlayerGameRepository playerGameDao){
        this.playerGameRoundDao = playerGameRoundDao;
        this.playerGameDao = playerGameDao;
    }

    @GetMapping("/round-report")
    public String showReport(){

        return "round-report";
    }
}
