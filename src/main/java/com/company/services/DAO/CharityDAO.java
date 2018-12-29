package com.company.services.DAO;

import com.company.domain.Charity;
import com.company.domain.Donor;
import lombok.NonNull;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CharityDAO extends BaseDAOImpl {

    @Override
    public <T> T create(T obj, Class<T> type, EntityManager em) {
        return super.create(obj, type, em);
    }

    public Charity create(Charity obj, @NonNull EntityManager em) {
        return super.create(obj, Charity.class, em);
    }

    public Charity getById(Long id, @NonNull EntityManager em) {
        return super.getById(Charity.class, id, em);
    }

    public Boolean delete(Long id, @NonNull EntityManager em) {
        return super.deleteById(Charity.class, id, em);
    }

    public List<Charity> getByName(String name, @NonNull EntityManager em) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Charity> criteria = cb.createQuery(Charity.class);
            Root root = criteria.from(Charity.class);
            criteria.where(cb.equal(root.get("name"), name));
            List<Charity> results = em.createQuery(criteria).getResultList();
            return results;
        } catch (Exception e) {
            return new ArrayList();
        }
    }
}

