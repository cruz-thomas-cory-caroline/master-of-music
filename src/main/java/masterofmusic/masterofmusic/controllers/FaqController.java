package masterofmusic.masterofmusic.controllers;

import masterofmusic.masterofmusic.SecurityConfiguration;
import masterofmusic.masterofmusic.models.PlayerGame;
import masterofmusic.masterofmusic.models.User;
import masterofmusic.masterofmusic.repositories.PlayerGameRepository;
import masterofmusic.masterofmusic.repositories.UserRepository;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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
        model.addAttribute("user", userDao.getOne(id));
        return "faqShow";
    }

    @GetMapping("/faq/{id}/edit")
    public String showEditForm(@PathVariable long id, Model viewModel){
        viewModel.addAttribute("user", userDao.getOne(id));
        return "faqEditpage";
    }

    @PostMapping("/faq/{id}/edit")
    public String editAd(HttpServletRequest request,
            @PathVariable long id,
//                         @RequestParam(name = "username") String username,
                         @RequestParam(name = "description") String desc,
//                         @RequestParam(name = "email") String email,
                         @RequestParam(name = "password") String password,
                         @RequestParam(name = "confirmPassword") String confirmPassword,
                         @ModelAttribute User user
    ){
        User current = (User) request.getSession().getAttribute("user");
        List<User> usersList = userDao.findAll();
        boolean passwordRequirements = (SecurityConfiguration.isValidPassword(password));
        if (!password.equals(confirmPassword)) {
            return "redirect:/faq/{id}/edit?invalidpw";
        } else if (!passwordRequirements) {
            return "redirect:/faq/{id}/edit?invalidpwRequirements";
        }else {
            User dbUser = userDao.getOne(id);
//            dbUser.setUsername(username);
            dbUser.setDescription(desc);
//            dbUser.setEmail(email);
            dbUser.setPassword(password);

            userDao.save(dbUser);
            return "redirect:/faq/" + dbUser.getId();
        }
    }
}
