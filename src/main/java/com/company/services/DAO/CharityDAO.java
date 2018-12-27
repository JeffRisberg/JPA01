package com.company.services.DAO;

import com.company.domain.Charity;
import lombok.NonNull;

import javax.persistence.EntityManager;

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
}

