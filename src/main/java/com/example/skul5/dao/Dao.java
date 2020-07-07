package com.example.skul5.dao;

import com.example.skul5.domain.Model;
import com.example.skul5.util.PersistenceInfo;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Scope("prototype")
public class Dao<T extends Model> {

    protected Class<T> type;

    @PersistenceContext(unitName = PersistenceInfo.Unit)
    protected final EntityManager em;

    public Dao(EntityManager em) {
        this.em = em;
    }

    public void setType(Class<T> type) {
        this.type = type;
    }

    public CriteriaQuery<T> getQuery() {
        return em.getCriteriaBuilder().createQuery(type);
    }

    @Transactional
    public List<T> readAll() throws DataAccessException {
        System.out.println("Reading all " + type.getSimpleName() + " from " + type.getPackage());
        CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(type);
        Root<T> root = query.from(type);
        query.select(root);
        return em.createQuery(query).getResultList();
    }

    @Transactional
    public List<T> Query(CriteriaQuery<T> query) throws DataAccessException {
        System.out.println("Reading criteria " + type.getSimpleName());
        return em.createQuery(query).getResultList();
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
