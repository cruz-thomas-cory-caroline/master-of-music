package masterofmusic.masterofmusic.controllers;

import masterofmusic.masterofmusic.models.User;
import masterofmusic.masterofmusic.repositories.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FindFriendController {
    private final UserRepository userDao;

    public FindFriendController(UserRepository userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/findafriend")
    public String findFriendPage(Model model){
        List<User> newLIST = userDao.findByOrderByUsernameAsc();
        for (User u : newLIST) {
            System.out.println(u.getUsername());
        }
        model.addAttribute("allUsers", newLIST);
        return "find-a-friend";
    }

}
