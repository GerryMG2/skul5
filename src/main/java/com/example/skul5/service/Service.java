package com.example.skul5.service;

import com.example.skul5.dao.Dao;
import com.example.skul5.domain.Model;
import com.sun.istack.internal.Nullable;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;

import javax.transaction.Transactional;
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

    public List<T> getAll() {
        try {
            return dao.readAll();
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Nullable
    public T findOne(Integer code) {
        try {
            return dao.read(code);
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Transactional
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

    @Transactional
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
