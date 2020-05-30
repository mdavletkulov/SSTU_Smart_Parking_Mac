package com.example.smartParking.controllers;

import com.example.smartParking.utils.ControllerUtils;
import com.example.smartParking.model.domain.Role;
import com.example.smartParking.model.domain.User;
import com.example.smartParking.model.domain.UserTemp;
import com.example.smartParking.model.domain.dto.CaptchaResponseDto;
import com.example.smartParking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    private static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    @Value("${recaptcha.secret}")
    private String recaptchaSecret;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
    @GetMapping("/registration")
    public String registration(@AuthenticationPrincipal User currentUser, Model model) {
        model.addAttribute("roles", Role.values());
        model.addAttribute("currentUser", currentUser);
        return "registration";
    }

    @PreAuthorize("hasAnyAuthority('MANAGER', 'ADMIN')")
    @PostMapping("/registration")
    public String addUser(@RequestParam("g-recaptcha-response") String captchaResponse,
                          @Valid UserTemp user,
                          BindingResult bindingResult,
                          Model model,
                          @AuthenticationPrincipal User currentUser) {
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("roles", Role.values());
        String url = String.format(CAPTCHA_URL, recaptchaSecret, captchaResponse);
        CaptchaResponseDto response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDto.class);

        if (!response.isSuccess()) {
            model.addAttribute("captchaError", "Заполните капчу");
        }

        if (user.getPassword2() == null || user.getPassword2().isEmpty()) {
            model.addAttribute("password2Error", "Поле подтверждения пароля пусто");
            bindingResult.addError(new ObjectError("password2Error", "Поле подтверждения пароля пусто"));
        }

        if (user.getPassword() != null && !user.getPassword().equals(user.getPassword2())) {
            model.addAttribute("passwordError", "Пароли не совпадают");
            bindingResult.addError(new ObjectError("passwordError", "Пароли не совпадают"));
        }

        if (user.getFirstName().matches(".*\\d.*")) {
            model.addAttribute("firstNameError", "Имя не должно содержать цифр");
            bindingResult.addError(new ObjectError("firstNameError", "Имя не должно содержать цифр"));
        }
        if (user.getSecondName().matches(".*\\d.*")) {
            model.addAttribute("secondNameError", "Фамилия не должно содержать цифр");
            bindingResult.addError(new ObjectError("secondNameError", "Фамилия не должно содержать цифр"));
        }
        if (user.getMiddleName().matches(".*\\d.*")) {
            model.addAttribute("middleNameError", "Отчество не должно содержать цифр");
            bindingResult.addError(new ObjectError("middleNameError", "Отчество не должно содержать цифр"));
        }
        if (user.getRole() == null) {
            model.addAttribute("roleError", "Выберите роль пользователя");
            bindingResult.addError(new ObjectError("roleError", "Выберите роль пользователя"));
        }

        if (user.getPassword().length() > 30) {
            model.addAttribute("passwordError", "Пароль не должен быть длиннее чем 30 знаков");
            bindingResult.addError(new ObjectError("passwordError", "Пароль не должен быть длиннее чем 30 знаков"));
        }

        if (bindingResult.hasErrors() || !response.isSuccess()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errors);
            return "registration";
        }

        userService.addUser(user, model);
        return "registration";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Пользователь успешно активирован");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Каод активации не найден");
        }

        return "login";
    }
}
