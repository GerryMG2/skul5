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

    public UserService(Dao<User> dao) {
        super(dao);
        dao.setType(User.class);
    }

    @Override
    public List<User> getAll() {
        return dao.readAll(cb -> {
            CriteriaQuery<User> query = cb.createQuery(User.class);
            Root<User> root = query.from(User.class);
            root.fetch("role", JoinType.LEFT);
            query.orderBy(cb.asc(root.get("id")));
            query.select(root);
            return query;
        });
    }
}
