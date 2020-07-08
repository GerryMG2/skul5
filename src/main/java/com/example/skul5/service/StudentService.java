package com.example.skul5.service;

import com.example.skul5.dao.Dao;
import com.example.skul5.domain.Record;
import com.example.skul5.domain.RecordId;
import com.example.skul5.domain.Student;
import org.springframework.context.annotation.Scope;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
@Scope("prototype")
public class StudentService extends Service<Student> {

    public StudentService(Dao<Student> dao) {
        super(dao);
        dao.setType(Student.class);
    }

    @Override
    public List<Student> getAll() {
        CriteriaQuery<Student> query = dao.getQuery();
        Root<Student> root = query.from(Student.class);
        root.fetch("templates/student", JoinType.RIGHT);
        query.select(root);
        return dao.readAll(query);
    }

    @Override
    public Student findOne(Integer code) {
        return dao.read(qb -> {
            ArrayList<Order> order = new ArrayList<>();
            CriteriaQuery<Student> query = qb.createQuery(Student.class);
            Root<Student> root = query.from(Student.class);
            Fetch<Student, Record> join = root.fetch("records", JoinType.LEFT);
            Fetch<Record, RecordId> join2 = join.fetch("primaryKey", JoinType.LEFT);
            join2.fetch("course", JoinType.LEFT);
            query.select(root);
            Root<Record> root2 = query.from(Record.class);
            query.where(qb.equal(root.get("id"), code));
            order.add(qb.asc(root2.get("primaryKey").get("year")));
            order.add(qb.asc(root2.get("primaryKey").get("semester")));
            query.orderBy(order);
            return query;
        });
    }
}
