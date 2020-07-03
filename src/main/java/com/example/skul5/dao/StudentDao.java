package com.example.skul5.dao;

import com.example.skul5.domain.Student;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class StudentDao extends Dao<Student> {

    public StudentDao(EntityManager em) {
        super(em);
        getAllQuery = "SELECT * FROM PUBLIC.student";
        type = Student.class;
    }
}
