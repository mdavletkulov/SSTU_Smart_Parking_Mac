package com.example.smartParking.controllers;

import com.example.smartParking.model.domain.Role;
import com.example.smartParking.model.domain.User;
import com.example.smartParking.repos.UserRepo;
import com.example.smartParking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(@AuthenticationPrincipal User currentUser, Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("currentUser", currentUser);
        return "admin/userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("edit/{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "admin/userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("edit/{userId}")
    public String editUser(@PathVariable Long userId, User changedUser, Model model) {
        User user = userService.getUserById(userId);
        boolean correct = userService.updateProfile(user, changedUser.getUsername(), changedUser.getFirstName(),
                changedUser.getSecondName(), changedUser.getMiddleName(), model, changedUser.getRole());
        if (correct) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Успешно");
        }
        return userEditForm(user, model);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("edit/changePassword/{user}")
    public String changeUserPassword(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        return "admin/userChangePassword";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("edit/changePassword/{userId}")
    public String changeUserPassword(@PathVariable String userId, @Valid User changedUser, BindingResult bindingResult, Model model) {
        User user = userService.getUserById(Long.valueOf(userId));
        userService.updatePassword(user, bindingResult, changedUser.getPassword(), changedUser.getPassword2(), model);
        return changeUserPassword(user, model);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user) {
        userService.saveUser(user, username, form);
        return "redirect:/user";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("delete/{userId}")
    public String userDelete(@AuthenticationPrincipal User currentUser,
                             @PathVariable String userId,
                             Model model) {
        userService.deleteUser(Long.valueOf(userId), model);
        return userList(currentUser, model);
    }
}
