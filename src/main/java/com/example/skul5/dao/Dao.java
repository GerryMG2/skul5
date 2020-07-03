package com.example.skul5.dao;

import com.example.skul5.domain.Model;
import com.example.skul5.util.PersistenceInfo;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

public abstract class Dao<T extends Model> {

    protected String getAllQuery;
    protected Class<T> type;

    @PersistenceContext(unitName = PersistenceInfo.Unit)
    protected final EntityManager em;

    public Dao(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public List<T> readAll() throws DataAccessException {
        System.out.println("Reading all " + type.getSimpleName() + " from " + type.getPackage());
        Query query = em.createNativeQuery(getAllQuery, type);
        return query.getResultList();
    }

    @Transactional
    public void create(T model) throws DataAccessException {
        em.persist(model);
    }

    @Transactional
    public T read(Integer id) throws DataAccessException {
        return em.find(type, id);
    }

    @Transactional
    public void update(T model) throws DataAccessException {
        em.merge(model);
        em.flush();
    }

    @Transactional
    public void delete(Integer id) throws DataAccessException {
        T record = em.find(type, id);
        em.remove(record);
    }

}
