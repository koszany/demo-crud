package com.codecool.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Properties;

@Controller
public class UserController {

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/new")
    String showCreateForm(User user) {
        return "create-user";
    }

    @PostMapping("/create")
    String createUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "create-user";
        }
        userRepository.save(user);
        return "redirect:/index";
    }


    @GetMapping("/index")
    String showIndex(Model model) {
        if (userRepository.count() > 0)
            model.addAttribute("users", userRepository.findAll());
        else
            model.addAttribute("users", null);
        System.out.println(userRepository.findAll());
        return "index";
    }

    @GetMapping("/edit/{id}")
    String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        model.addAttribute("user", user);
        return "update-user";
    }

    @PostMapping("/update/{id}")
    String updateUser (@PathVariable("id") long id, @Valid User user, BindingResult result, Model model) {
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/index";
    }
}
