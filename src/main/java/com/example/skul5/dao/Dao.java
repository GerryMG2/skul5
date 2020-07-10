package com.example.skul5.dao;

import com.example.skul5.domain.Model;
import com.example.skul5.util.PersistenceInfo;

import java.util.List;
import java.util.function.Function;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Table;
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

    public CriteriaQuery<T> getQuery() {
        return em.getCriteriaBuilder().createQuery(type);
    }

    @Transactional
    public List<T> readAll() throws DataAccessException {
        System.out.println("Reading all " + type.getSimpleName() + " from " + type.getPackage());
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);
        query.select(root);
        query.orderBy(builder.asc(root.get("id")));
        return em.createQuery(query).getResultList();
    }

    @Transactional
    public List<T> readAll(CriteriaQuery<T> query) throws DataAccessException {
        System.out.println("Reading criteria " + type.getSimpleName());
        query.getRoots().forEach(r -> {
            query.orderBy(em.getCriteriaBuilder().asc(r.get("id")));
        });
        return em.createQuery(query).getResultList();
    }

    @Transactional
    public void create(T model) throws DataAccessException {
        em.persist(model);
    }
    
    
    
    public  <G>  T  getOneByOneField(String field,G value ) throws DataAccessException {
    	String tableName = this.type.getAnnotation(Table.class).name();
    	Query q = em.createNativeQuery("SELECT * FROM "+tableName +" WHERE "+field+ " = :value ;",this.type);
    	q.setParameter("value", value);
    	return (T) q.getSingleResult();
    }

    public <K> T findBy(String field, K value) {
        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<T> query = qb.createQuery(type);
        Root<T> root = query.from(type);
        query.where(qb.equal(root.get(field), value));
        query.select(root);
        return em.createQuery(query).getSingleResult();
    }

    @Transactional
    public T read(Integer id) throws DataAccessException {
        return em.find(type, id);
    }
    
    public <H> List<H> getListOfSommethingWithQuerryFilterByListString(String qr,Class<H> t) {
    	
    	
    	Query q = em.createNativeQuery(qr,t);
    	
    	return q.getResultList();
    }

    @Transactional
    public T read(Function<CriteriaBuilder, CriteriaQuery<T>> queryFunction) throws DataAccessException {
        CriteriaQuery<T> query = queryFunction.apply(em.getCriteriaBuilder());
        List<T> res = em.createQuery(query).getResultList();
        if (res.size() > 0) {
            return res.get(0);
        }
        return null;
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
