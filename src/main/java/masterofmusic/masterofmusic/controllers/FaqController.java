package masterofmusic.masterofmusic.controllers;

import masterofmusic.masterofmusic.models.PlayerGame;
import masterofmusic.masterofmusic.models.User;
import masterofmusic.masterofmusic.repositories.PlayerGameRepository;
import masterofmusic.masterofmusic.repositories.UserRepository;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FaqController {
    private final UserRepository userDao;

    public FaqController(UserRepository userDao) {
        this.userDao = userDao;
    }


    @GetMapping("/faq")
    public String viewFaq(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ArrayList<User> userIsAdmin = new ArrayList<>();

        for (User user1 : userDao.findAll()) {
            if (user1.isAdmin()) {
                userIsAdmin.add(user1);
            }
        }
        model.addAttribute("users", userIsAdmin);
        System.out.println(user.isAdmin());
        return "faq";

    }

    @GetMapping("/faq/{id}")
    public String viewPost(@PathVariable long id, Model model) {
        model.addAttribute("user", userDao.getOne(id).isAdmin());
        return "faqShow";
    }

    @GetMapping("/faq/{id}/edit")
    public String showEditForm(@PathVariable long id, Model viewModel){
        viewModel.addAttribute("user", userDao.getOne(id));
        return "faqEditpage";
    }

    @PostMapping("/faq/{id}/edit")
    public String editAd(
            @PathVariable long id,
            @RequestParam(name = "username") String username,
            @RequestParam(name = "description") String desc
    ){

        User dbUser = userDao.getOne(id);
        dbUser.setUsername(username);
        dbUser.setDescription(desc);
        userDao.save(dbUser);
        return "redirect:/faq/" + dbUser.getId();
    }
}
