package com.example.skul5.service;

import com.example.skul5.dao.Dao;
import com.example.skul5.domain.Course;
import com.example.skul5.domain.Student;
import org.springframework.context.annotation.Scope;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
@Scope("prototype")
public class StudentService extends Service<Student> {

    private final Dao<Course> dao1;

    public StudentService(Dao<Student> dao, Dao<Course> dao1) {
        super(dao);
        dao.setType(Student.class);
        this.dao1 = dao1;
        dao1.setType(Course.class);
    }

    public Student retrieveOne(Integer code) {
        return dao.read(qb -> {
            ArrayList<Order> order = new ArrayList<>();
            CriteriaQuery<Student> query = qb.createQuery(Student.class);
            Root<Student> root = query.from(Student.class);
            root.fetch("records", JoinType.LEFT)
                    .fetch("primaryKey")
                    .fetch("course", JoinType.LEFT);
            query.where(qb.equal(root.get("id"), code));
            query.select(root);
            return query;
        });
    }

    public List<Course> getCourses() {
        return dao1.readAll();
    }
}
