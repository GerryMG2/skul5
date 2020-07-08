package com.example.skul5.controllers;

import com.example.skul5.domain.Record;
import com.example.skul5.domain.Student;
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
        ModelAndView vm = new ModelAndView();
        Student s = service.retrieveOne(id);
        if(s != null) {
            vm.setViewName("student/records");
            vm.addObject("student", s);
        } else {
            vm.setViewName("util/404");
            vm.addObject("message", "Estudiante no encontrado");
        }
        return vm;
    }

    @GetMapping("/student/{id}/add/record")
    public ModelAndView addRecord(@PathVariable(value = "id") Integer id) {
        ModelAndView vm = new ModelAndView();
        Student s = service.findOne(id);
        if(s != null) {
            vm.setViewName("student/course");
            vm.addObject("student", s);
            vm.addObject("course", new Record());
            vm.addObject("courses", service.getCourses());
        } else {
            vm.setViewName("util/404");
            vm.addObject("message", "Estudiante no encontrado");
        }
        return vm;
    }
}
