package com.example.skul5.dao;

import com.example.skul5.domain.Department;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class DepartmentDao extends Dao<Department> {

    public DepartmentDao(EntityManager em) {
        super(em);
        getAllQuery = "SELECT * FROM DEPARTMENT;";
        type = Department.class;
    }

}
