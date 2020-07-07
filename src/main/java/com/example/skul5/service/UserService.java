package com.example.skul5.service;

import com.example.skul5.dao.Dao;
import com.example.skul5.domain.Role;
import com.example.skul5.domain.User;
import org.springframework.context.annotation.Scope;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;

@org.springframework.stereotype.Service
@Scope("prototype")
public class UserService extends Service<User>{

    public UserService(Dao<User> dao) {
        super(dao);
        dao.setType(User.class);
    }

    @Override
    public List<User> getAll() {
        CriteriaQuery<User> query = dao.getQuery();
        Root<User> root = query.from(User.class);
        root.join("role", JoinType.LEFT);
        query.select(root);
        return dao.execute(query);
    }
}
