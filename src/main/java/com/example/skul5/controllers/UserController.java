package com.example.skul5.controllers;

import com.example.skul5.domain.School;
import com.example.skul5.domain.User;
import com.example.skul5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@Component
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
        service.ConfigureType(User.class);
    }

    @GetMapping(value = {"/users", "/user/list"})
    public ModelAndView Index() {
        ModelAndView vm = new ModelAndView("user/list");
        vm.addObject("users", service.getAll());
        return vm;
    }

    @GetMapping(value = "/user/add")
    public ModelAndView add() {
        ModelAndView vm = new ModelAndView("user/add");
        vm.addObject("user", new User());
        vm.addObject("municipalities", service.getMunicipalities());
        return vm;
    }

    @PostMapping("/user/add")
    public ModelAndView register(@Valid @ModelAttribute User user, BindingResult result) {
        ModelAndView vm = new ModelAndView();
        if (result.hasErrors()) {
            vm.setViewName("/user/add");
            vm.addObject("user", user);
            vm.addObject("municipalities", service.getMunicipalities());
        } else {
            System.out.println("El registro es " + user.getName());
            service.save(user);
            vm.setViewName("util/success");
            vm.addObject("message", "Usuario " + user.getName() + " guardado");
            vm.addObject("url", "/users");
        }
        return vm;
    }

    @GetMapping("/user/edit/{id}")
    public ModelAndView edit(@PathVariable(value = "id") Integer id) {
        ModelAndView vm = new ModelAndView("user/add");
        vm.addObject("user", service.findOne(id));
        vm.addObject("municipalities", service.getMunicipalities());
        return vm;
    }
}
