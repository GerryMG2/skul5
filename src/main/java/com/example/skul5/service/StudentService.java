package com.example.skul5.service;

import com.example.skul5.dao.Dao;
import com.example.skul5.domain.*;
import com.example.skul5.domain.Record;
import org.springframework.context.annotation.Scope;

import javax.persistence.criteria.*;
import java.util.List;

@org.springframework.stereotype.Service
@Scope("prototype")
public class StudentService extends Service<Student> {


    public StudentService(Dao<Student> dao) {
        super(dao);
        dao.setType(Student.class);
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
        return dao.readAll(cb -> {
            CriteriaQuery<Course> query = cb.createQuery(Course.class);
            Root<Course> root = query.from(Course.class);
            query.select(root);
            query.orderBy(cb.asc(root.get("id")));
            return query;
        });
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

    public List<School> retrieveSchools() {
        return dao.readAll(qb -> {
            CriteriaQuery<School> query = qb.createQuery(School.class);
            Root<School> root = query.from(School.class);
            query.orderBy(qb.asc(root.get("name")));
            query.select(root);
            return query;
        });
    }
}
