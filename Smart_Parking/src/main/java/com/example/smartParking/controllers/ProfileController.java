package com.example.smartParking.controllers;

import com.example.smartParking.model.domain.User;
import com.example.smartParking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);

        return "admin/profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@AuthenticationPrincipal User user,
                                @RequestParam String username,
                                @RequestParam String firstName,
                                @RequestParam String secondName,
                                @RequestParam String middleName,
                                Model model) {


        if (userService.updateProfile(user, username, firstName, secondName, middleName, model, null)) {
            return "redirect:/user/profile";
        }
        return getProfile(model, user);
    }

    @GetMapping("/changePassword")
    public String changePassword(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        return "admin/profileChangePassword";
    }

    @PostMapping("/changePassword")
    public String changePassword(@AuthenticationPrincipal User user,
                                 @Valid User userChange,
                                 BindingResult bindingResult,
                                 Model model) {
        userService.updatePassword(user, bindingResult, userChange.getPassword(), userChange.getPassword2(), model);
        return changePassword(model, user);
    }

}
