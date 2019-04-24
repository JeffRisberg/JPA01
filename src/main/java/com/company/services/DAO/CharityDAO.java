package com.company.services.DAO;

import com.company.domain.Charity;
import lombok.NonNull;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class CharityDAO extends BaseTemplateDAOImpl<Charity> {

    public CharityDAO() {
        super(Charity.class);
    }

    public Charity create(Charity obj, @NonNull EntityManager em) {
        return super.create(obj, em);
    }

    public Charity getById(Long id, @NonNull EntityManager em) {
        return super.getById(id, em);
    }

    public Boolean delete(Long id, @NonNull EntityManager em) {
        return super.deleteById(id, em);
    }
}

