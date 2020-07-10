package com.example.skul5.service;

import com.example.skul5.dao.Dao;
import com.example.skul5.domain.Course;
import com.example.skul5.domain.Record;
import com.example.skul5.domain.Student;
import org.springframework.context.annotation.Scope;

import javax.persistence.criteria.*;
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

    public Record retrieveRecord(Integer year, Integer cycle, Integer courseId, Integer studentId) {
        return dao.read(cb -> {
            CriteriaQuery<Record> query = cb.createQuery(Record.class);
            Root<Record> root = query.from(Record.class);
            Fetch<Object, Object> pk = root.fetch("primaryKey");
            pk.fetch("student", JoinType.LEFT);
            pk.fetch("course", JoinType.LEFT);
            Predicate p = cb.equal(root.get("primaryKey").get("student").get("id"), studentId);
            p = cb.and(p, cb.equal(root.get("primaryKey").get("year"), year));
            p = cb.and(p, cb.equal(root.get("primaryKey").get("semester"), cycle));
            p = cb.and(p, cb.equal(root.get("primaryKey").get("course").get("id"), courseId));
            query.where(p);
            query.select(root);
            return query;
        });
    }
}