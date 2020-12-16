package masterofmusic.masterofmusic.Controllers;

import masterofmusic.masterofmusic.repositories.PostRepository;
import masterofmusic.masterofmusic.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ForumController {
    private final PostRepository postDao;
    private final UserRepository userDao;

    public ForumController(PostRepository postDao, UserRepository userDao) {
        this.postDao = postDao;
        this.userDao = userDao;
    }

    @GetMapping("/forum")
    public String showForumPage(Model model) {
        model.addAttribute("posts", postDao.findAll());
        return "forum";
    }

}
