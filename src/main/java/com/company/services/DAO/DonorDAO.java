package com.company.services.DAO;

import com.company.domain.Donor;
import lombok.NonNull;

import javax.persistence.EntityManager;

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
}

