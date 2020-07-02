package com.example.skul5.service;

import com.example.skul5.dao.Dao;
import com.example.skul5.domain.School;

@org.springframework.stereotype.Service
public class SchoolService extends Service<School> {

    public SchoolService(Dao<School> dao) {
        super(dao);
    }
}
