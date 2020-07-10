package com.example.skul5.controllers;

import com.example.skul5.domain.Record;
import com.example.skul5.domain.Student;
import com.example.skul5.domain.groupStudentRecord;
import com.example.skul5.domain.*;
import com.example.skul5.service.StudentService;

import java.util.List;

import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashSet;

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



    @GetMapping("/student/{id}")
    public ModelAndView records(@PathVariable(value = "id") Integer id) {
        ModelAndView vm = new ModelAndView();
        Student s = service.retrieveOne(id);
        if (s != null) {
            vm.setViewName("student/records");
            vm.addObject("student", s);
        } else {
            vm.setViewName("util/404");
            vm.addObject("message", "Estudiante no encontrado");
        }
        return vm;
    }

    @GetMapping("/student/{id}/record/add")
    public ModelAndView addRecord(@PathVariable(value = "id") Integer id) {
        ModelAndView vm = new ModelAndView();
        Record record = new Record();
        record.setPrimaryKey(new RecordId());
        setStudentView(id, record, vm);
        return vm;
    }

    @PostMapping("/student/{id}/record/add")
    public ModelAndView addRecord(@PathVariable(value = "id") Integer id, @Valid @ModelAttribute Record record, BindingResult result) {
        ModelAndView vm = new ModelAndView();
        if (result.hasErrors()) {
            setStudentView(id, record, vm);
        } else {
            vm.setViewName("util/success");
            vm.addObject("message", "Estudante " + id + " actualizado");
            vm.addObject("url", "/student/" + id);
            Student s = service.retrieveOne(id);
            if (s == null) {
                vm.setViewName("util/404");
                vm.addObject("message", "Estudiante no encontrado");
                return vm;
            } else {
                boolean wasFound = false;
                record.getPrimaryKey().setStudent(s);
                for (Record r : s.getRecords()) {
                    RecordId pk = r.getPrimaryKey();
                    if (pk.getYear().equals(record.getPrimaryKey().getYear())
                            && pk.getSemester().equals(record.getPrimaryKey().getSemester())
                            && pk.getCourse().getId().equals(record.getPrimaryKey().getCourse().getId())) {
                        r.setGrade(record.getGrade());
                        System.out.println("lo encontre");
                        wasFound = true;
                        break;
                    }
                }
                if (!wasFound) {
                    System.out.println("Se metido");
                    s.getRecords().add(record);
                }
                service.save(s);
            }
        }
        return vm;
    }

    private void setStudentView(Integer id, Record record, ModelAndView vm) {
        Student s = service.retrieveOne(id);
        if (s != null) {
            record.getPrimaryKey().setStudent(s);
            vm.setViewName("student/course");
            vm.addObject("record", record);
            vm.addObject("courses", service.getCourses());
        } else {
            vm.setViewName("util/404");
            vm.addObject("message", "Estudiante no encontrado");
        }
    }






}
