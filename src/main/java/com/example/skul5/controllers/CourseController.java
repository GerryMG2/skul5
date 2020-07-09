package com.example.skul5.controllers;

import com.example.skul5.domain.Course;
import com.example.skul5.service.Service;
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
public class CourseController {

    private final Service<Course> service;

    @Autowired
    public CourseController(Service<Course> service) {
        this.service = service;
        service.ConfigureType(Course.class);
    }

    @GetMapping(value = {"/courses", "/course/list"})
    public ModelAndView index() {
        ModelAndView vm = new ModelAndView("course/courses");
        vm.addObject("courses", service.getAll());
        return vm;
    }

    @GetMapping("/course/add")
    public ModelAndView add() {
        ModelAndView vm = new ModelAndView("course/add");
        vm.addObject("course", new Course());
        return vm;
    }

    @PostMapping("/course/add")
    public ModelAndView register(@Valid @ModelAttribute Course course, BindingResult result) {
        ModelAndView vm = new ModelAndView();
        if (result.hasErrors()) {
            vm.setViewName("/course/add");
            vm.addObject("course", course);
        } else {
            System.out.println("El registro es " + course.getName());
            service.save(course);
            vm.setViewName("util/success");
            vm.addObject("message", "Curso " + course.getName() + " guardado");
            vm.addObject("url", "/courses");
        }
        return vm;
    }

    @GetMapping("/course/edit/{id}")
    public ModelAndView edit(@PathVariable(value = "id") Integer id) {
        ModelAndView vm = new ModelAndView("course/add");
        vm.addObject("course", service.findOne(id));
        return vm;
    }

}
