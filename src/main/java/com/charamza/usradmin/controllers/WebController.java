package com.charamza.usradmin.controllers;

import com.charamza.usradmin.models.User;
import com.charamza.usradmin.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class WebController {

    UserService service;

    @Autowired
    public WebController(UserService service) {
        this.service = service;
    }

    @PostMapping("/search-by-id")
    public String showById(@ModelAttribute User user, Model model) {
        model.addAttribute("usersId", service.findUserById(user.getId()));
        return "search-id";
    }

    @PostMapping("/search-by-name")
    public String showByName(@ModelAttribute User user, Model model) {
        model.addAttribute("usersName", service.findByName(user.getName()));
        return "search-name";
    }

    @PostMapping("/search-by-name-and-date")
    public String showByNameAndDate(@ModelAttribute User user, Model model, String fromDate, String toDate) {
        model.addAttribute("usersNameAndDate",
                service.findAllByNameBetweenDates(user.getName(),
                        LocalDate.parse(fromDate), LocalDate.parse(toDate)));
        return "search-name-date";
    }

    @PostMapping("/edit-user")
    public String editUser(@ModelAttribute User user) {
        user.setCreationDate(LocalDate.now());
        service.saveUser(user);
        return "redirect:/";
    }

    // Get mapping bellow

    @GetMapping("/")
    public String showHomepage(Model model) {
        return findPaginated(1, model);
    }

    @GetMapping("/status/{id}")
    public String changeUserStatus(@PathVariable long id) {
        service.changeUserStatus(id);
        return "redirect:/";
    }

    @GetMapping("/active-users")
    public String showActiveUsers(Model model) {
        model.addAttribute("activeUsers", service.findAllActive());
        return "search-active";
    }

    @GetMapping("/inactive-users")
    public String showInactiveUsers(Model model) {
        model.addAttribute("inactiveUsers", service.findAllInactive());
        return "search-inactive";
    }

    @GetMapping("/user/{id}")
    public String showUserDetail(@PathVariable Long id, Model model) {
        model.addAttribute("userDetail", service.findUserById(id));
        return "user-detail";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return "redirect:/";
    }

    // Helper method for pagination, implemented at the end
    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable int pageNo, Model model) {
        int pageSize = 4;

        Page<User> page = service.findPaginated(pageNo, pageSize);
        List<User> userList = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("userList", userList);
        return "index";
    }
}