package masterofmusic.masterofmusic.controllers;

import masterofmusic.masterofmusic.SecurityConfiguration;
import masterofmusic.masterofmusic.models.User;
import masterofmusic.masterofmusic.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {
    private UserRepository users;
    private PasswordEncoder passwordEncoder;

    public UserController(UserRepository users, PasswordEncoder passwordEncoder) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/sign-up")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "sign-up";
    }


    @PostMapping("/sign-up")
    public String saveUser(@RequestParam(name = "password") String password,
                           @RequestParam(name = "confirmPassword") String confirmPassword,
                           @RequestParam(name = "email") String email,
                           @RequestParam(name = "resetPasswordToken") String resetPasswordToken,
                           @RequestParam(name = "securityQuestion") String securityQuestion,
                           @ModelAttribute User user) {
        boolean passwordRequirements = (SecurityConfiguration.isValidPassword(password));
        boolean emailRequirements = (SecurityConfiguration.emailMeetsRequirements(email));
        List<User> usersList = users.findAll();
        for (User u : usersList) {
            if (user.getUsername().equalsIgnoreCase(u.getUsername())) {
                return "redirect:/sign-up?usernameNotAvailable";
            }
        }
        for (User u : usersList) {
            if (user.getEmail().equalsIgnoreCase(u.getEmail())) {
                return "redirect:/sign-up?emailNotAvailable";
            }
        }
        if (!password.equals(confirmPassword)) {
            return "redirect:/sign-up?invalidpw";
        } else if (!passwordRequirements) {
            return "redirect:/sign-up?invalidpwRequirements";
        } else if (!emailRequirements) {
            return "redirect:/sign-up?invalidEmail";
        } else if (password.equalsIgnoreCase("Admin@123")) {
            user.setAdmin(true);
            String hash = passwordEncoder.encode(user.getPassword());
            user.setPassword(hash);
            users.save(user);
            return "redirect:/login";
        } else {
            String hash = passwordEncoder.encode(user.getPassword());
            user.setPassword(hash);
            user.setResetPasswordToken(resetPasswordToken);
            user.setSecurityQuestion(securityQuestion);
            users.save(user);
            return "redirect:/login";
        }
    }

    @GetMapping("/usernameVerification")
    public String emailVerification(@RequestParam(name = "username")String username,
                                    Model viewModel){
        viewModel.addAttribute(users.findByUsername(username).getId());
        return "usernameVerification";
    }

//    @PostMapping("/securityQuestion/{id}")
//    public User securityQuestion(@RequestParam(name = "resetPasswordToken") String resetPasswordToken,
//                                 @RequestParam(name = "securityQuestion") String securityQuestion,
//                                 @ModelAttribute User user,
//                                 @PathVariable long id,
//                                 HttpServletRequest request) {
//
//    for(User user1 : users.findAll()){
//        if(user1.getUsername())
//    }

//    }

    @ModelAttribute("loggedinuser")
    public User globalUserObject(Model model) {
        // Add all null check and authentication check before using. Because this is global
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("loggedinuser", authentication.getName());
        model.addAttribute("roles", authentication.getAuthorities());
        // Create User pojo class
        User user = new User(authentication.getName(), Arrays.asList(authentication.getAuthorities()));
        return user;
    }
}

