package com.example.smartParking.service;

import com.example.smartParking.utils.ControllerUtils;
import com.example.smartParking.model.domain.Role;
import com.example.smartParking.model.domain.User;
import com.example.smartParking.model.domain.UserTemp;
import com.example.smartParking.repos.UserRepo;
import com.example.smartParking.repos.UserTempRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserTempRepo userTempRepo;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepo.findByUsername(username);
        if (user == null) throw new IllegalArgumentException("Пользователь не найден");
        if (!user.isEnabled()) throw new IllegalArgumentException("Пользователь не активирован.");
        if (user.getRole().equals(Role.MOBILE_USER))
            throw new IllegalArgumentException("Вы не имеете доступа к системе администрирования. \n" +
                    "Воспользуйтесь нашим мобильным приложением.");
        return user;
    }

    public void addUser(UserTemp user, Model model) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.addAttribute("usernameError", "Пользователь с таким E-mail уже существует!");
            return;
        }
        user.setEnabled(false);
        user.setActivationCode(UUID.randomUUID().toString());
        String password = user.getPassword();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userTempRepo.save(user);

        boolean sended = sendMessage(user, password);

        if (!sended) {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Произошла ошибка при отправке сообщения");
        } else {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", String.format("Для заверешния регистрации пользователю стоит проверить почту: %s", user.getUsername()));
        }

    }

    private boolean sendMessage(User user, String password) {
        if (!user.getUsername().isEmpty()) {
            String message = String.format(
                    "Здравствуйте, %s! \n" +
                            "Добро пожаловать в систему Умная парковка. Ваш пароль: %s. Пожалуйста, пройдите по ссылке для окончания регистрации" +
                            ":http://localhost:8080/activate/%s",
                    user.getFullName(),
                    password,
                    user.getActivationCode()
            );
            try {
                mailSender.send(user.getUsername(), "Activation code", message);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else return false;
    }

    private boolean sendMessage(UserTemp user, String password) {
        if (!user.getUsername().isEmpty()) {
            String message = String.format(
                    "Здравствуйте, %s! \n" +
                            "Добро пожаловать в систему Умная парковка. Ваш пароль: %s. Пожалуйста, пройдите по ссылке для окончания регистрации" +
                            ":http://localhost:8080/activate/%s",
                    user.getFullName(),
                    password,
                    user.getActivationCode()
            );
            try {
                mailSender.send(user.getUsername(), "Activation code", message);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else return false;
    }

    public boolean activateUser(String code) {
        UserTemp userTemp = userTempRepo.findByActivationCode(code);
        if (userTemp == null) {
            return false;
        }
        User user = new User();
        user.setUsername(userTemp.getUsername());
        user.setFirstName(userTemp.getFirstName());
        user.setSecondName(userTemp.getSecondName());
        user.setMiddleName(userTemp.getMiddleName());
        user.setPassword(userTemp.getPassword());
        user.setEnabled(true);
        user.setActivationCode(null);
        user.setRole(userTemp.getRole());
        userRepo.save(user);
        userTempRepo.delete(userTemp);

        return true;
    }

    public Iterable<User> findAll() {
        return userRepo.findAll();
    }

    public void saveUser(User user, String username, Map<String, String> form) {
        user.setUsername(username);
        Set<Object> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.setRole(Role.valueOf(key));
            }
        }

        userRepo.save(user);
        refreshSession();
    }

    public boolean updateProfile(User user, String email,
                                 String firstName, String secondName, String middleName, Model model, Role role) {
        String userEmail = user.getUsername();
        boolean correctChanging = true;
        if (email == null || email.isBlank()) {
            model.addAttribute("usernameError", "Поле электронной почты было пустым");
            correctChanging = false;
        }
        if (firstName == null || firstName.isBlank()) {
            model.addAttribute("firstNameError", "Поле имени было пустым");
            correctChanging = false;
        }
        if (secondName == null || secondName.isBlank()) {
            model.addAttribute("secondNameError", "Поле фамилии было пустым");
            correctChanging = false;
        }

        if (correctChanging) {
            boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                    (userEmail != null && !userEmail.equals(email));
            if (isEmailChanged) user.setUsername(email);
//            if (email != null && !email.isBlank())
//                user.setActivationCode(UUID.randomUUID().toString());
//            if (isEmailChanged) sendMessage(user);
            user.setFirstName(firstName);
            user.setSecondName(secondName);
            user.setMiddleName(middleName);
            if (role != null) {
                changeRole(user, role);
            }
            userRepo.save(user);
            refreshSession();
        }
        return correctChanging;
    }

    public void changeRole(User user, Role role) {
        if (user.getRole().equals(role)) return;
        else {
            user.setRole(role);
        }
    }

    public void updatePassword(User user, BindingResult bindingResult, String password, String password2, Model model) {
        if (!password.isBlank() && !password2.isBlank() && password.equals(password2) && !bindingResult.hasErrors()) {
            user.setPassword(passwordEncoder.encode(password));
            userRepo.save(user);
            refreshSession();
            model.addAttribute("message", "Пароль успешно изменен!");
        } else {
            if (password.isEmpty()) {
                model.addAttribute("passwordError", "Поле пароля пустое");
            } else if (!password.equals(password2)) model.addAttribute("passwordError", "Пароли не совпадают");
            else if (password.length() > 30) {
                bindingResult.addError(new ObjectError("passwordError", "Пароль не должен быть длиннее чем 30 знаков"));
            } else if
            (bindingResult.hasErrors()) {
                Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
                model.mergeAttributes(errors);
            }
            if (password2.isEmpty())
                model.addAttribute("password2Error", "Поле подтверждения пароля пустое");
        }
    }

    private void refreshSession() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
        updatedAuthorities.add(Role.ADMIN);

        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);

        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    public void deleteUser(Long userId, Model model) {
        Optional<User> user = userRepo.findById(userId);
        if (user.isPresent()) {
            userRepo.deleteById(userId);
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Пользователь не найден");
            return;
        }
        user = userRepo.findById(userId);
        if (user.isEmpty()) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "Пользователь успешно удален");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Пользователь не был удален");
        }
    }

    public User getUserById(Long userId) {
        Optional<User> userDB = userRepo.findById(userId);
        return userDB.get();
    }

}
