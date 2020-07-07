package com.example.skul5.service;

import com.example.skul5.dao.Dao;
import com.example.skul5.domain.Municipality;
import com.example.skul5.domain.School;
import org.springframework.context.annotation.Scope;

import java.util.List;

@org.springframework.stereotype.Service
@Scope("prototype")
public class SchoolService extends Service<School> {

    private final Dao<Municipality> municipalityDao;

    public SchoolService(Dao<School> dao, Dao<Municipality> municipalityDao) {
        super(dao);
        dao.setType(School.class);
        this.municipalityDao = municipalityDao;
        this.municipalityDao.setType(Municipality.class);
    }

    public List<Municipality> getMunicipalities(){
        return municipalityDao.readAll();
    }
}
