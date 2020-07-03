package com.example.skul5.controllers;

import com.example.skul5.domain.School;
import com.example.skul5.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Component
public class SchoolController {

    private final Service<School> service;

    @Autowired
    public SchoolController(Service<School> service) {
        this.service = service;
        service.ConfigureType(School.class);
    }

    @GetMapping("/school")
    public ModelAndView Index() {
        ModelAndView vm = new ModelAndView();
        vm.setViewName("school");
        vm.addObject("schools", service.getAll());
        return vm;
    }
}
