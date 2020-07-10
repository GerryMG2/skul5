package com.example.skul5.service;

import com.example.skul5.dao.Dao;
import com.example.skul5.domain.Model;
import com.example.skul5.domain.User;
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
    
    public <G> T getOneByOneField(String field,G value) {
    	return (T) dao.<G>getOneByOneField(field, value);
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

    public <K> T findBy(String field, K value){
        return dao.findBy(field, value);
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

    public boolean delete(Integer code) {
        try {
            dao.delete(code);
            return true;
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
