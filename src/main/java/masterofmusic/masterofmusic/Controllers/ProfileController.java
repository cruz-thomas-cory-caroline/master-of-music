package masterofmusic.masterofmusic.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProfileController {

//    private final /*someRepo*/ profileDao

    @GetMapping("/profile/{id}")
    public String viewProfile(@PathVariable long id, Model model){
        model.addAttribute("profile");
        return "/profile";
    }

}
