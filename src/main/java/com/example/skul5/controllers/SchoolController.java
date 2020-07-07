package com.example.skul5.controllers;

import com.example.skul5.domain.School;
import com.example.skul5.service.SchoolService;
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
public class SchoolController {

    private final SchoolService service;

    @Autowired
    public SchoolController(SchoolService service) {
        this.service = service;
    }

    @GetMapping(value = {"/schools", "/school/list"})
    public ModelAndView Index() {
        ModelAndView vm = new ModelAndView("school/list");
        vm.addObject("schools", service.getAll());
        return vm;
    }

    @GetMapping("/school/add")
    public ModelAndView add() {
        ModelAndView vm = new ModelAndView("school/add");
        vm.addObject("school", new School());
        vm.addObject("municipalities", service.getMunicipalities());
        return vm;
    }

    @PostMapping("/school/add")
    public ModelAndView register(@Valid @ModelAttribute School school, BindingResult result) {
        ModelAndView vm = new ModelAndView();
        if (result.hasErrors()) {
            vm.setViewName("/school/add");
            vm.addObject("school", school);
            vm.addObject("municipalities", service.getMunicipalities());
        } else {
            System.out.println("El registro es " + school.getName());
            service.save(school);
            vm.setViewName("util/success");
            vm.addObject("message", "Escuela " + school.getName() + " guardada");
            vm.addObject("url", "/schools");
        }
        return vm;
    }

    @GetMapping("/school/edit/{id}")
    public ModelAndView edit(@PathVariable(value = "id") Integer id) {
        ModelAndView vm = new ModelAndView("school/add");
        vm.addObject("school", service.findOne(id));
        vm.addObject("municipalities", service.getMunicipalities());
        return vm;
    }

}
