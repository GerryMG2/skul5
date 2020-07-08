package com.example.skul5.service;

import com.example.skul5.dao.Dao;
import com.example.skul5.domain.Municipality;
import com.example.skul5.domain.User;
import org.springframework.context.annotation.Scope;

import javax.persistence.criteria.*;
import java.util.List;

@org.springframework.stereotype.Service
@Scope("prototype")
public class UserService extends Service<User> {

    private final Dao<Municipality> municipalityDao;

    public UserService(Dao<User> dao, Dao<Municipality> municipalityDao) {
        super(dao);
        dao.setType(User.class);
        municipalityDao.setType(Municipality.class);
        this.municipalityDao = municipalityDao;
    }

    @Override
    public List<User> getAll() {
        CriteriaQuery<User> query = dao.getQuery();
        Root<User> root = query.from(User.class);
        root.fetch("role", JoinType.LEFT);
        query.select(root);
        return dao.readAll(query);
    }

    public List<Municipality> getMunicipalities(){
        return municipalityDao.readAll();
    }
}
