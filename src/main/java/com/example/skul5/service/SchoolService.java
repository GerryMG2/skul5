package com.example.skul5.service;

import com.example.skul5.dao.Dao;
import com.example.skul5.domain.School;
import org.springframework.context.annotation.Scope;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.List;

@org.springframework.stereotype.Service
@Scope("prototype")
public class SchoolService extends Service<School> {

    public SchoolService(Dao<School> dao) {
        super(dao);
        dao.setType(School.class);
    }

    @Override
    public List<School> getAll() {
        return dao.readAll(cb -> {
            CriteriaQuery<School> query = cb.createQuery(School.class);
            Root<School> root = query.from(School.class);
            root.fetch("municipality", JoinType.LEFT);
            query.select(root);
            return query;
        });
    }
}
