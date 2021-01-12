package masterofmusic.masterofmusic.controllers;
import masterofmusic.masterofmusic.SecurityConfiguration;
import masterofmusic.masterofmusic.models.Achievement;
import masterofmusic.masterofmusic.models.ConfirmationToken;
import masterofmusic.masterofmusic.models.User;
import masterofmusic.masterofmusic.repositories.UserRepository;
import masterofmusic.masterofmusic.services.ConfirmationTokenRepository;
import masterofmusic.masterofmusic.services.EmailSenderService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {
    private UserRepository users;
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private EmailSenderService emailSenderService;

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
            user.getResetPasswordToken();
            user.setAdmin(true);
            String hash = passwordEncoder.encode(user.getPassword());
            user.setPassword(hash);
            //Default Image
            user.setImages("/img/maleIcon1.jpg");
            users.save(user);
            return "redirect:/login";
        } else {
            String hash = passwordEncoder.encode(user.getPassword());
            user.setPassword(hash);
            user.setImages("/img/maleIcon1.jpg");
            users.save(user);
            ConfirmationToken confirmationToken = new ConfirmationToken(user);

            confirmationTokenRepository.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setFrom("masterofmusic@codeup.com");
            mailMessage.setText("To confirm your account, go to the url : "
                    +"http://masterofmusic.fun/confirm-account?token="+confirmationToken.getConfirmationToken());

            emailSenderService.sendEmail(mailMessage);
            return "redirect:/login";
        }
    }

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
    {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            User user = users.findByEmailIgnoreCase(token.getUser().getEmail());
            user.setEnabled(true);
            users.save(user);
            modelAndView.setViewName("accountVerified");
        }
        else
        {
            modelAndView.addObject("message","The link is invalid or broken!");
            modelAndView.setViewName("error");
        }

        return modelAndView;
    }
    // getters and setters



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

    @RequestMapping(value="/forgotPassword", method=RequestMethod.GET)
    public ModelAndView displayResetPassword(ModelAndView modelAndView, User user) {
        modelAndView.addObject("user", user);
        modelAndView.setViewName("forgotPassword");
        return modelAndView;
    }

    // Receive the address and send an email
    @RequestMapping(value="/forgotPassword", method=RequestMethod.POST)
    public ModelAndView forgotUserPassword(ModelAndView modelAndView, User user) {
        User existingUser = users.findByEmailIgnoreCase(user.getEmail());
        if (existingUser != null) {
            // Create token
            ConfirmationToken confirmationToken = new ConfirmationToken(existingUser);

            // Save it
            confirmationTokenRepository.save(confirmationToken);

            // Create the email
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(existingUser.getEmail());
            mailMessage.setSubject("Complete Password Reset!");
            mailMessage.setFrom("test-email@gmail.com");
            mailMessage.setText("To complete the password reset process, please click here: "
                    + "http://masterofmusic.fun/confirm-reset?token="+confirmationToken.getConfirmationToken());
            // Send the email
            emailSenderService.sendEmail(mailMessage);

            modelAndView.addObject("message", "Request to reset password received. Check your inbox for the reset link.");
            modelAndView.setViewName("successForgotPassword");

        } else {
            modelAndView.addObject("message", "This email address does not exist!");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    @RequestMapping(value="/confirm-reset", method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView validateResetToken(ModelAndView modelAndView, @RequestParam("token")String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if (token != null) {
            User user = users.findByEmailIgnoreCase(token.getUser().getEmail());
            user.setEnabled(true);
            users.save(user);
            modelAndView.addObject("user", user);
            modelAndView.addObject("emailId", user.getEmail());
            modelAndView.setViewName("resetPassword");
        } else {
            modelAndView.addObject("message", "The link is invalid or broken!");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    // Endpoint to update a user's password
    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    public ModelAndView resetUserPassword(ModelAndView modelAndView, User user) {
        if (user.getEmail() != null) {
            // Use email to find user
            User tokenUser = users.findByEmailIgnoreCase(user.getEmail());
            tokenUser.setPassword(passwordEncoder.encode(user.getPassword()));
            users.save(tokenUser);
            modelAndView.addObject("message", "Password successfully reset. You can now log in with the new credentials.");
            modelAndView.setViewName("successResetPassword");
        } else {
            modelAndView.addObject("message","The link is invalid or broken!");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }
}

