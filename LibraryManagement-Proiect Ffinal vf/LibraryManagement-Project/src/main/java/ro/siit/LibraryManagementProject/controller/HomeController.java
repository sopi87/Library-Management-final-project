package ro.siit.LibraryManagementProject.controller;

import ro.siit.LibraryManagementProject.model.User;
import ro.siit.LibraryManagementProject.service.BookService;
import ro.siit.LibraryManagementProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;


     // This controller will identify the user who is logged in
     //If he is a member, then it will redirect him to the member dashboard.
     //If he is a librarian, then he will redirect him to the librarian dashboard.
    @GetMapping("/")
    public String home() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Optional<User> user = userService.getUserByUsername(username);
        if (user.isPresent() && user.get().getRole().equalsIgnoreCase("member")) {
            return "redirect:/dashboard/member";
        } else if (user.isPresent() && user.get().getRole().equalsIgnoreCase("librarian")) {
            return "redirect:/dashboard/librarian";
        } else {
            return "redirect:/logout";
        }
    }


    @GetMapping("/dashboard/member")
    public String memberDashboard(Model model) {
        model.addAttribute("booksList", bookService.listOfBooks());
        return "member_dashboard";
    }


    @GetMapping("/dashboard/librarian")
    public String librarianDashboard(Model model) {
        model.addAttribute("booksList", bookService.listOfBooks());
        return "librarian_dashboard";
    }
}
