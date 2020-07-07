package com.example.skul5.controllers;

import com.example.skul5.domain.User;
import com.example.skul5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Component
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
        service.ConfigureType(User.class);
    }

    @GetMapping(value = {"/users", "/users/list"})
    public ModelAndView Index() {
        ModelAndView vm = new ModelAndView("user/list");
        vm.addObject("users", service.getAll());
        return vm;
    }
}
