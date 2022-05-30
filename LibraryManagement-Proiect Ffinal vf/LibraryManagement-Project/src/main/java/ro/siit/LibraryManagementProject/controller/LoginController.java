package ro.siit.LibraryManagementProject.controller;

import ro.siit.LibraryManagementProject.model.User;
import ro.siit.LibraryManagementProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    UserService userService;


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "login";
    }



    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }


    @PostMapping("/register")
    public String registerForSubmission(@Valid User user, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            Optional<User> existingUser = userService.getUserByUsername(user.getUsername());
            if (existingUser.isPresent()) {
                model.addAttribute("userAlreadyExists", true);
            } else {
                userService.save(user);
                model.addAttribute("successfully", true);
                model.addAttribute("user", new User());
            }
        }
        return "register";
    }

}
