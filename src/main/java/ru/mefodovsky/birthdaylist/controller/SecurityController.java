package ru.mefodovsky.birthdaylist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mefodovsky.birthdaylist.dto.UserDto;
import ru.mefodovsky.birthdaylist.entity.User;
import ru.mefodovsky.birthdaylist.repository.UserRepository;
import ru.mefodovsky.birthdaylist.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class SecurityController {

    private final UserService userService;
    private final UserRepository userRepository;

    public SecurityController(UserService userService,
                              UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result, Model model) {

        User existingUser = userRepository.findByEmail(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null &&
                !existingUser.getEmail().isEmpty()) {

            result.rejectValue("email", null,
                    "Этот e-mail адрес уже занят.");

        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/login?registration_success";
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/users/user")
    public String user(@RequestParam Long userId, Model model) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            model.addAttribute("user", optionalUser.get());
            return "user-info";
        }
        return "redirect:/user-list";
    }

}
