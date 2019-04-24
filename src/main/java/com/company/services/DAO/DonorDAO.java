package com.company.services.DAO;

import com.company.domain.Donor;
import lombok.NonNull;

import javax.persistence.EntityManager;

public class DonorDAO extends BaseTemplateDAOImpl<Donor> {

    public DonorDAO() {
        super(Donor.class);
    }

    public Donor create(Donor obj, @NonNull EntityManager em) {
        return super.create(obj, em);
    }

    public Donor getById(Long id, @NonNull EntityManager em) {
        return super.getById(id, em);
    }

    public Boolean delete(Long id, @NonNull EntityManager em) {
        return super.deleteById(id, em);
    }
}

