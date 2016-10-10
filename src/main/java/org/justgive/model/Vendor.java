package org.justgive.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.justgive.database.DatedDatabaseItem;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The <i>Vendor</i> entity.
 *
 * @author Jeffrey Risberg
 * @since April 2014
 */
@Entity
@Table(name = "vendors")
public class Vendor extends DatedDatabaseItem {

    @Size(max = 150)
    @Column(name = "vendorname")
    protected String name;

    public Vendor() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
