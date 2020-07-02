package com.example.skul5.service;

import com.example.skul5.dao.Dao;
import com.example.skul5.domain.Student;

@org.springframework.stereotype.Service
public class StudentService extends Service<Student>{

    public StudentService(Dao<Student> dao) {
        super(dao);
    }
}
