package com.example.skul5.controllers;

import com.example.skul5.domain.Record;
import com.example.skul5.domain.Student;
import com.example.skul5.domain.groupStudentRecord;
import com.example.skul5.domain.*;
import com.example.skul5.service.StudentService;

import java.util.List;

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

@Controller
@Component
public class StudentController {

    private final StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    private void setStudentView(Integer id, Record record, ModelAndView vm) {
        Student s = service.retrieveOne(id);
        if (s != null) {
            record.getPrimaryKey().setStudent(s);
            vm.setViewName("student/course");
            vm.addObject("record", record);
            vm.addObject("courses", service.getCourses());
        } else {
            setNotFound(vm);
        }
    }

    private void setNotFound(ModelAndView vm) {
        vm.setViewName("util/404");
        vm.addObject("message", "Estudiante no encontrado");
    }

    @GetMapping("/student/{id}")
    public ModelAndView records(@PathVariable(value = "id") Integer id) {
        ModelAndView vm = new ModelAndView();
        Student s = service.retrieveOne(id);
        if (s != null) {
            vm.setViewName("student/records");
            vm.addObject("student", s);
        } else {
            setNotFound(vm);
        }
        return vm;
    }

    @GetMapping("/student/add")
    public ModelAndView add() {
        ModelAndView vm = new ModelAndView("student/add");
        vm.addObject("student", new Student());
        vm.addObject("schools", service.retrieveSchools());
        return vm;
    }

    @GetMapping("/student/{id}/edit")
    public ModelAndView add(@PathVariable(name = "id") Integer id) {
        ModelAndView vm = new ModelAndView("student/add");
        Student s = service.retrieveOne(id);
        if (s != null) {
            vm.addObject("student", s);
            vm.addObject("schools", service.retrieveSchools());
        } else {
            setNotFound(vm);
        }
        return vm;
    }

    @PostMapping("/student/add")
    public ModelAndView register(@Valid @ModelAttribute Student student, BindingResult result) {
        ModelAndView vm = new ModelAndView();
        if (result.hasErrors()) {
            result.getAllErrors().forEach(e -> {
                System.out.println(e.getCode());
                System.out.println(e.getDefaultMessage());
            });
            vm.setViewName("student/add");
            vm.addObject("student", student);
            vm.addObject("schools", service.retrieveSchools());
        } else {
            System.out.println("El registro es " + student.getName());
            service.save(student);
            vm.setViewName("util/success");
            vm.addObject("message", "Estudiante " + student.getName() + " guardado");
            vm.addObject("url", "/students");
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

    @GetMapping("/student/{id}/record/edit/{year}/{cycle}/{course}")
    public ModelAndView addRecord(
            @PathVariable(value = "id") Integer id,
            @PathVariable(value = "year") Integer year,
            @PathVariable(value = "cycle") Integer cycle,
            @PathVariable(value = "course") Integer course
    ) {
        ModelAndView vm = new ModelAndView();
        Record r = service.retrieveRecord(year, cycle, course, id);
        if (r == null) {
            setNotFound(vm);
            return vm;
        }
        setStudentView(id, r, vm);
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
                setNotFound(vm);
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

    @GetMapping("/students")
    public ModelAndView students(@Param("tipo") String tipo, @Param("sea") String sea) {
        ModelAndView vm = new ModelAndView();
        if( sea != null && sea != "") {
        	String add = "";
        	System.out.println(tipo);
        	if(tipo.equals("Nombre")) {
        		add = "Upper(b.name) like ";
        	}else {
        		add = "Upper(b.last_name) like";
        	}
        	  List<groupStudentRecord> lista = this.service.getListOfSommethingWithQuerryFilterByListString(
        			  "select a.id_student as id, b.name as nombre,b.last_name as apellido, sum(case when a.grade > 5 then 1 else 0 end) as materiasa," +
              		"sum(case when a.grade <= 5 then 1 else 0 end) as materiasr" +
              		", avg(a.grade) promedio " +
              		"from record a"
              		+ " left outer join student b on a.id_student = b.id "
              		+ "where "
              		+ add
              		+ "'%"
              		+ sea.toUpperCase()
              		+"%' "
              +"  group by a.id_student, b.name, b.last_name  ;", groupStudentRecord.class );
              System.out.println(lista);
              vm.addObject("lista", lista);
              vm.addObject("search", sea);
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
    
    @GetMapping("/search")
    public ModelAndView search() {
        ModelAndView vm = new ModelAndView();
        vm.setViewName("student/listagrupal");
        return vm;
    }

}