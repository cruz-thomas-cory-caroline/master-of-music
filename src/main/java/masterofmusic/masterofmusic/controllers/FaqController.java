package masterofmusic.masterofmusic.controllers;

import masterofmusic.masterofmusic.repositories.PlayerGameRepository;
import masterofmusic.masterofmusic.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

@Controller
public class FaqController {
    private final UserRepository userDao;

    public FaqController(UserRepository userDao){
        this.userDao = userDao;



    }
}
