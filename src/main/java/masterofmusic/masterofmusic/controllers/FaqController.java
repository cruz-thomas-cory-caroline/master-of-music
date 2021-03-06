package masterofmusic.masterofmusic.controllers;
import masterofmusic.masterofmusic.SecurityConfiguration;
import masterofmusic.masterofmusic.models.User;
import masterofmusic.masterofmusic.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;


@Controller
public class FaqController {
    private final UserRepository userDao;
    private PasswordEncoder passwordEncoder;

    public FaqController(UserRepository userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/faq")
    public String viewFaq(Model model) {
        ArrayList<User> userIsAdmin = new ArrayList<>();
        if (!SecurityContextHolder.getContext().getAuthentication().getName().equalsIgnoreCase("anonymousUser")) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user_db = userDao.findById(user.getId());
            model.addAttribute("user", user_db);
        }

        for (User user1 : userDao.findAll()) {
            if (user1.isAdmin()) {
                userIsAdmin.add(user1);
            }
        }

        model.addAttribute("users", userIsAdmin);
        return "faq";

    }

}
