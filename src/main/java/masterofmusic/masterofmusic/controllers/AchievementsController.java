package masterofmusic.masterofmusic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AchievementsController {

    @GetMapping("/achievement")
    public String showBadges() {

        return "achievements";
    }


}
