package com.example.skul5.controllers;

import com.example.skul5.domain.UserLogin;
import com.example.skul5.domain.Student;
import com.example.skul5.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@Component
public class MainController {

    private final Service<Student> service;

    @Autowired
    public MainController(Service<Student> service) {
        this.service = service;
    }
    
    
    

    @GetMapping(value = {"/", "/index", "/inicio"})
    public ModelAndView Index() {
        ModelAndView vm = new ModelAndView();
        vm.setViewName("login");
        vm.addObject("loginObject", new UserLogin());
        return vm;
    }
    
    @PostMapping("/login")
    public ModelAndView login(@Valid @ModelAttribute UserLogin log, BindingResult result) {
        ModelAndView vm = new ModelAndView();
        vm.setViewName("index");
        if (!result.hasErrors()) {
        	vm.setViewName("index");
            //TODO: add validation 
        }
        
        return vm;
    }

    @GetMapping("/listado")
    public ModelAndView Student() {
        ModelAndView vm = new ModelAndView();
        vm.addObject("students", service.getAll());
        vm.setViewName("list");
        return vm;
    }

    @PostMapping("/register")
    public ModelAndView register(@Valid @ModelAttribute Student student, BindingResult result) {
        ModelAndView vm = new ModelAndView();
        if (!result.hasErrors()) {
            System.out.println("El registro es " + student.getId());
            vm.addObject("student", new Student());
            service.save(student);
        }
        vm.setViewName("index");
        return vm;
    }

    @PostMapping(value = "/delete", params = "action=delete")
    public ModelAndView delete(@RequestParam(value = "id") int id) {
        ModelAndView vm = new ModelAndView();
        vm.addObject("id", id);
        vm.addObject("deleted", service.delete(id));
        vm.setViewName("deleted");
        return vm;
    }

    @PostMapping(value = "/delete", params = "action=edit")
    public ModelAndView Edit(@RequestParam(value = "id") int id) {
        ModelAndView vm = new ModelAndView();
        Student student = service.findOne(id);
        student.setId(id);
        vm.addObject("student", student);
        vm.setViewName("index");
        return vm;
    }
}
