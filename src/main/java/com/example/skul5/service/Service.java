package com.example.skul5.service;

import com.example.skul5.dao.Dao;
import com.example.skul5.domain.Model;
import com.example.skul5.domain.Municipality;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
@Scope("prototype")
public class Service<T extends Model> {

    protected final Dao<T> dao;

    public Service(Dao<T> dao) {
        this.dao = dao;
    }

    public void ConfigureType(Class<T> type){
        dao.setType(type);
    }
    
    public <H> List<H> getListOfSommethingWithQuerryFilterByListString(String qr, Class<H> t){
    	return dao.<H>getListOfSommethingWithQuerryFilterByListString(qr, t);
    }

    public List<T> getAll() {
        try {
            return dao.readAll();
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    public T findOne(Integer code) {
        try {
            return dao.read(code);
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public T findByField(String field, Object value) {
        return dao.findByField(field, value);
    }

    public void save(T model) {
        try {
            if (model.getId() == null) {
                dao.create(model);
            } else {
                dao.update(model);
            }
        } catch (DataAccessException ex) {
            ex.printStackTrace();
        }
    }

    public List<Municipality> getMunicipalities() {
        return dao.readAll(cb -> {
            CriteriaQuery<Municipality> query = cb.createQuery(Municipality.class);
            Root<Municipality> root = query.from(Municipality.class);
            query.select(root);
            query.orderBy(cb.asc(root.get("id")));
            return query;
        });
    }

}