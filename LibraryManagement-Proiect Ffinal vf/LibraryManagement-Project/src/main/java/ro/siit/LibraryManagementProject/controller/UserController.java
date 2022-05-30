package ro.siit.LibraryManagementProject.controller;

import ro.siit.LibraryManagementProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;


    @GetMapping("/members/list")    //vizualizare lista membrii
    public String getAllMembersList(Model model) {
        model.addAttribute("membersList", userService.getUsersByRole("member"));
        return "library_members_list";
    }
}
