package masterofmusic.masterofmusic.controllers;
import masterofmusic.masterofmusic.SecurityConfiguration;
import masterofmusic.masterofmusic.models.User;
import masterofmusic.masterofmusic.repositories.UserRepository;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ArrayList<User> caroline = new ArrayList<>();
        ArrayList<User> cory = new ArrayList<>();
        ArrayList<User> cruz = new ArrayList<>();
        ArrayList<User> thomas = new ArrayList<>();

        ArrayList<User> userIsAdmin = new ArrayList<>();

        for (User user1 : userDao.findAll()) {
            if (user1.isAdmin()) {
                userIsAdmin.add(user1);
            }
        }

        for (User user1 : userDao.findAll()) {
            if (user1.getUsername().equalsIgnoreCase("cory")) {
                cory.add(user1);
            } else if (user1.getUsername().equalsIgnoreCase("caroline")) {
                caroline.add(user1);
            } else if (user1.getUsername().equalsIgnoreCase("cruz")) {
                cruz.add(user1);
            } else if (user1.getUsername().equalsIgnoreCase("thomas")) {
                thomas.add(user1);
            }
        }
        model.addAttribute("users", userIsAdmin);
        model.addAttribute("caroline", caroline);
        model.addAttribute("cory", cory);
        model.addAttribute("cruz", cruz);
        model.addAttribute("thomas", thomas);
        System.out.println(user.isAdmin());
        return "faq";

    }

    @GetMapping("/faq/{id}")
    public String viewPost(@PathVariable long id, Model model) {
        model.addAttribute("user", userDao.getOne(id));
        return "faqShow";
    }

    @GetMapping("/faq/{id}/edit")
    public String showEditForm(@PathVariable long id, Model viewModel) {
        viewModel.addAttribute("user", userDao.getOne(id));
        return "faqEditpage";
    }

    @PostMapping("/faq/{id}/edit")
    public String editAd(
            @PathVariable long id,
            @RequestParam(name = "description") String desc,
            @RequestParam(name = "adminImage") String adminImage,
            @ModelAttribute User user
    ) {

        User dbUser = userDao.getOne(id);
        dbUser.setImages(adminImage);
        dbUser.setDescription(desc);
        userDao.save(dbUser);
        return "redirect:/faq/" + dbUser.getId();

    }
}
