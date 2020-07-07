package com.example.skul5.controllers;

import com.example.skul5.domain.User;
import com.example.skul5.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

    private void retry(ModelAndView vm, User user) {
        vm.setViewName("/user/add");
        vm.addObject("user", user);
        vm.addObject("municipalities", service.getMunicipalities());
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
        boolean badpassword = false;
        if (user.getId() == null) {
            if (user.getPassword().isEmpty() || user.getPassword().length() > 256) {
                badpassword = true;
                vm.addObject("password", "La contraseña no debe estar vacia o tener mas de 256 caracteres");
            }
        }
        if (result.hasErrors() || badpassword) {
            retry(vm, user);
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
