package masterofmusic.masterofmusic.controllers;

import masterofmusic.masterofmusic.models.Comment;
import masterofmusic.masterofmusic.models.Post;
import masterofmusic.masterofmusic.models.User;
import masterofmusic.masterofmusic.repositories.CommentRepository;
import masterofmusic.masterofmusic.repositories.PostRepository;
import masterofmusic.masterofmusic.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ForumController {
    private final PostRepository postDao;
    private final UserRepository userDao;
    private final CommentRepository commentDao;

    public ForumController(PostRepository postDao, UserRepository userDao, CommentRepository commentDao) {
        this.postDao = postDao;
        this.userDao = userDao;
        this.commentDao = commentDao;
    }

    @GetMapping("/forum")
    public String showForumPage(Model model) {
        model.addAttribute("posts", postDao.findAll());
        model.addAttribute("post", new Post());
        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString().equalsIgnoreCase("anonymousUser")) {
            model.addAttribute("user", (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }
        return "forum";
    }

    @PostMapping("/forum/post")
    public String submitPost(@ModelAttribute Post post) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        post.setOwner(loggedInUser);
        Post dbPost = postDao.save(post);
        return "redirect:/forum";
    }

    @PostMapping("/forum/comment/{id}")
    public String submitComment(@PathVariable long id,
                                @RequestParam(name = "body") String body) {
        User hardCodedUser = userDao.getOne(3L);
        Post parentPost = postDao.getOne(id);
        Comment commentToSave = new Comment(body, parentPost, hardCodedUser);
        commentDao.save(commentToSave);
        return "redirect:/forum";
    }

    @PostMapping("/post/{id}/delete")
    public String deletePost(@PathVariable long id) {
        postDao.deleteById(id);
        return "redirect:/forum";
    }

    @PostMapping("/comment/{id}/delete")
    public String deleteComment(@PathVariable long id) {
        commentDao.deleteById(id);
        return "redirect:/forum";
    }

}
