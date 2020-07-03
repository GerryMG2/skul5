package com.example.skul5.dao;

import com.example.skul5.domain.School;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class SchoolDao extends Dao<School> {

    public SchoolDao(EntityManager em) {
        super(em);
        getAllQuery = "SELECT * FROM SCHOOL;";
        type = School.class;
    }

}
