package com.company.services.DAO;

import com.company.domain.Donor;
import lombok.NonNull;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class DonorDAO extends BaseDAOImpl {

    @Override
    public <T> T create(T obj, Class<T> type, EntityManager em) {
        return super.create(obj, type, em);
    }

    public Donor create(Donor obj, @NonNull EntityManager em) {
        return super.create(obj, Donor.class, em);
    }

    public Donor getById(Long id, @NonNull EntityManager em) {
        return super.getById(Donor.class, id, em);
    }

    public Boolean delete(Long id, @NonNull EntityManager em) {
        return super.deleteById(Donor.class, id, em);
    }

    public List<Donor> getByName(String name, @NonNull EntityManager em) {
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Donor> criteria = cb.createQuery(Donor.class);
            Root root = criteria.from(Donor.class);
            criteria.where(cb.equal(root.get("name"), name));
            List<Donor> results = em.createQuery(criteria).getResultList();
            return results;
        } catch (Exception e) {
            return new ArrayList();
        }
    }
}

