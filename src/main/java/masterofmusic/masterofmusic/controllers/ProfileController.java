package masterofmusic.masterofmusic.controllers;

import masterofmusic.masterofmusic.models.User;
import masterofmusic.masterofmusic.repositories.StatsRepository;
import masterofmusic.masterofmusic.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {

    private final UserRepository userDao;
    private final StatsRepository statsDao;

    public ProfileController(UserRepository userDao, StatsRepository statsDao){
        this.userDao = userDao;
        this.statsDao = statsDao;
    }

    @GetMapping("/profile")
    public String viewProfile(
            Model model
    ){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", userDao.getOne(user.getId()));
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
        userDao.save(user);
        System.out.println(avatarSelected);
        model.addAttribute("user", user);
        return "profile";
    }


}
