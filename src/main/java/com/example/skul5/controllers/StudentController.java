package com.example.skul5.controllers;

import com.example.skul5.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Component
public class StudentController {

    private final StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/student/{id}/records")
    public ModelAndView records(@PathVariable(value = "id") Integer id) {
        ModelAndView vm = new ModelAndView("student/records");
        vm.addObject("student", service.findOne(id));
        return vm;
    }
}
