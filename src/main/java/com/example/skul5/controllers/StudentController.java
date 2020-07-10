package com.example.skul5.controllers;

import com.example.skul5.domain.Record;
import com.example.skul5.domain.Student;
import com.example.skul5.domain.groupStudentRecord;
import com.example.skul5.service.StudentService;

import java.util.List;

import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
    
    
    @GetMapping("/students")
    public ModelAndView students(@Param("search") String sea) {
        ModelAndView vm = new ModelAndView();
        if( sea != null) {
        	  List<groupStudentRecord> lista = this.service.getListOfSommethingWithQuerryFilterByListString("select a.id_student as id, b.name as nombre,b.last_name as apellido, sum(case when a.grade > 5 then 1 else 0 end) as materiasa," + 
              		"sum(case when a.grade <= 5 then 1 else 0 end) as materiasr" + 
              		", avg(a.grade) promedio " + 
              		"from record a"
              		+ " left outer join student b on a.id_student = b.id "
              		+ "where "
              		+ "b.name like "
              		+ "'%"
              		+ sea
              		+"%' "
              		+ "and "
              		+ "b.last_name like '%"
              		+ sea +
              		"%'  group by a.id_student, b.name, b.last_name  ;", groupStudentRecord.class );
              System.out.println(lista);
              vm.addObject("lista", lista);
        }else {
        	  List<groupStudentRecord> lista = this.service.getListOfSommethingWithQuerryFilterByListString("select a.id_student as id, b.name as nombre,b.last_name as apellido, sum(case when a.grade > 5 then 1 else 0 end) as materiasa," + 
              		"sum(case when a.grade <= 5 then 1 else 0 end) as materiasr" + 
              		", avg(a.grade) promedio " + 
              		"from record a"
              		+ " left outer join student b on a.id_student = b.id "
              		+"  group by a.id_student, b.name , b.last_name ;", groupStudentRecord.class );
              System.out.println(lista.get(0).getNombre());
              vm.addObject("lista", lista);
        }
        vm.setViewName("student/listagrupal");
       
        return vm;
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
