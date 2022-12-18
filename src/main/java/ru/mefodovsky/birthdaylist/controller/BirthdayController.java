package ru.mefodovsky.birthdaylist.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.mefodovsky.birthdaylist.entity.Birthday;
import ru.mefodovsky.birthdaylist.entity.User;
import ru.mefodovsky.birthdaylist.repository.BirthdayRepository;
import ru.mefodovsky.birthdaylist.repository.UserRepository;
import ru.mefodovsky.birthdaylist.service.LogService;

import java.security.Principal;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/list")
public class BirthdayController {
    private final UserRepository userRepository;
    private final BirthdayRepository birthdayRepository;
    private final LogService logService;

    public BirthdayController(BirthdayRepository birthdayRepository,
                              UserRepository userRepository,
                              LogService logService) {
        this.birthdayRepository = birthdayRepository;
        this.userRepository = userRepository;
        this.logService = logService;
    }

    @GetMapping()
    public ModelAndView getBirthdayList(Principal principal) {
        ModelAndView mav = new ModelAndView("birthday-list");
        mav.addObject("user", userRepository.findByEmail(principal.getName()));
        return mav;
    }

    @GetMapping("/showAddForm")
    public ModelAndView showAddForm() {
        ModelAndView mav = new ModelAndView("add-birthday");
        Birthday birthday = new Birthday();
        mav.addObject("birthday", birthday);
        return mav;
    }

    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(@RequestParam Long birthdayId) {
        ModelAndView mav = new ModelAndView("add-birthday");
        Optional<Birthday> optionalBirthday = birthdayRepository.findById(birthdayId);
        Birthday birthday = new Birthday();
        if (optionalBirthday.isPresent()) {
            birthday = optionalBirthday.get();
        }
        mav.addObject("birthday", birthday);
        return mav;
    }

    @PostMapping("/saveBirthday")
    public String saveBirthday(@ModelAttribute Birthday birthday, Principal principal) {
        Optional<Birthday> optionalBirthdayToBeChanged = birthdayRepository.findById(birthday.getId());
        User user = userRepository.findByEmail(principal.getName());

        if (optionalBirthdayToBeChanged.isPresent() &&
                optionalBirthdayToBeChanged.get().getOwner().getId().equals(user.getId())) {

            birthday.setOwner(user);
            logService.addEntry(user, "[CHANGE] " + optionalBirthdayToBeChanged.get() + " to " + birthday);
            birthdayRepository.save(birthday);
        } else {
            birthday.setOwner(user);
            birthday.setId(0);
            Birthday newBirthday = birthdayRepository.save(birthday);
            logService.addEntry(user, "[ADD] " + newBirthday);
        }

        return "redirect:/list";
    }

    @GetMapping("/deleteBirthday")
    public String deleteBirthday(@RequestParam long birthdayId, Principal principal) {
        Optional<Birthday> optionalBirthday = birthdayRepository.findById(birthdayId);
        User user = userRepository.findByEmail(principal.getName());

        if (optionalBirthday.isPresent() && optionalBirthday.get().getOwner().getId().equals(user.getId())) {
            birthdayRepository.deleteById(birthdayId);
            logService.addEntry(user, "[DELETE] " + optionalBirthday.get());
        }
        return "redirect:/list";
    }

}
