package masterofmusic.masterofmusic.controllers;
import masterofmusic.masterofmusic.SecurityConfiguration;
import masterofmusic.masterofmusic.models.User;
import masterofmusic.masterofmusic.repositories.UserRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.*;
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
                           @RequestParam(name = "isAdmin", defaultValue = "false") boolean isAdmin,
                           @ModelAttribute User user) {
        boolean passwordRequirements = (SecurityConfiguration.isValidPassword(password));
        boolean emailRequirements = (SecurityConfiguration.emailMeetsRequirements(email));
        List<User> usersList = users.findAll();
        for (User u : usersList) {
            if (user.getUsername().equalsIgnoreCase(u.getUsername())){
                return "redirect:/sign-up?usernameNotAvailable";
            }
        }
        for (User u : usersList) {
            if (user.getEmail().equalsIgnoreCase(u.getEmail())){
                return "redirect:/sign-up?emailNotAvailable";
            }
        }
        if (!password.equals(confirmPassword)) {
            return "redirect:/sign-up?invalidpw";
        } else if (!passwordRequirements) {
            return "redirect:/sign-up?invalidpwRequirements";
        } else if (!emailRequirements) {
            return "redirect:/sign-up?invalidEmail";
        } else {
            user.setAdmin(isAdmin);
            String hash = passwordEncoder.encode(user.getPassword());
            user.setPassword(hash);
            users.save(user);
            return "redirect:/login";
        }

    }
}

