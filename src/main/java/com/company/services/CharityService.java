package com.company.services;

import com.company.domain.Charity;
import com.company.services.DAO.CharityDAO;

import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class CharityService extends AbstractService {
    private static CharityDAO dao = new CharityDAO();

    @Inject
    public CharityService(MySQLConnectionFactory mysqlConnectionFactory) {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("javax.persistence.nonJtaDataSource", mysqlConnectionFactory.getOrCreateDataSource());
        props.put("javax.persistence.transactionType", "RESOURCE_LOCAL");
        this.emf = Persistence.createEntityManagerFactory("persistenceUnitName", props);
    }

    public Charity getById(Long id) {
        final AtomicReference<Charity> td = new AtomicReference<>();
        doWork(em -> td.set(dao.getById(id, em)));
        return td.get();
    }

    public List<Charity> getCharities(int limit, int offset) {
        final AtomicReference<List<Charity>> td = new AtomicReference<>();
        doWork(em -> td.set(dao.listAll(Charity.class, em)));
        return td.get();
    }
}

