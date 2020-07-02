package com.example.skul5.dao.catalog;

import com.example.skul5.dao.Dao;
import com.example.skul5.domain.School;
import com.example.skul5.domain.Student;
import com.example.skul5.util.PersistenceInfo;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class SchoolDao implements Dao<School> {

    private static final String getAllQuery = "SELECT * FROM PUBLIC.school";

    @PersistenceContext(unitName = PersistenceInfo.Unit)
    private final EntityManager em;

    public SchoolDao(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<School> readAll() throws DataAccessException {
        System.out.println("Reading all " + School.class.getName());
        Query query = em.createNativeQuery(getAllQuery, School.class);
        return query.getResultList();
    }

    @Override
    public void create(School model) throws DataAccessException {
        em.persist(model);
    }

    @Override
    public School read(Integer id) throws DataAccessException {
        return em.find(School.class, id);
    }

    @Override
    public void update(School model) throws DataAccessException {
        em.merge(model);
        em.flush();
    }

    @Override
    public void delete(Integer id) throws DataAccessException {
        Student student = em.find(Student.class, id);
        em.remove(student);
    }
}
