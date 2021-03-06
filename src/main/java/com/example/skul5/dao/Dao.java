package com.example.skul5.dao;

import com.example.skul5.domain.Model;
import com.example.skul5.util.PersistenceInfo;

import java.util.List;
import java.util.function.Function;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
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

    @Transactional
    public void create(T model) throws DataAccessException {
        em.persist(model);
    }

    @Transactional
    public T read(Integer id) throws DataAccessException {
        return em.find(type, id);
    }

    @Transactional
    public <K> K read(Function<CriteriaBuilder, CriteriaQuery<K>> queryFunction) throws DataAccessException {
        CriteriaQuery<K> query = queryFunction.apply(em.getCriteriaBuilder());
        try {
            return em.createQuery(query).getSingleResult();
        } catch (NoResultException | NonUniqueResultException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Transactional
    public List<T> readAll() throws DataAccessException {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);
        query.select(root);
        query.orderBy(builder.asc(root.get("id")));
        return em.createQuery(query).getResultList();
    }

    @Transactional
    public <K> List<K> readAll(Function<CriteriaBuilder, CriteriaQuery<K>> queryFunction) {
        CriteriaQuery<K> query = queryFunction.apply(em.getCriteriaBuilder());
        return em.createQuery(query).getResultList();
    }

    @Deprecated
    public <G> T getOneByOneField(String field, G value) throws DataAccessException {
        String tableName = this.type.getAnnotation(Table.class).name();
        Query q = em.createNativeQuery("SELECT * FROM " + tableName + " WHERE " + field + " = :value ;", this.type);
        q.setParameter("value", value);
        return (T) q.getSingleResult();
    }

    public T findByField(String field, Object value) {
        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<T> query = qb.createQuery(type);
        Root<T> root = query.from(type);
        query.where(qb.equal(root.get(field), value));
        query.select(root);
        return em.createQuery(query).getSingleResult();
    }

    public <H> List<H> getListOfSommethingWithQuerryFilterByListString(String qr, Class<H> t) {
        Query q = em.createNativeQuery(qr, t);
        return q.getResultList();
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