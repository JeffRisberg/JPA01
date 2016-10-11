package org.justgive.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.justgive.database.DatabaseItem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * A Fee Type provides information about a service charge added to a transaction.
 */
@Entity
@Table(name = "feetypes")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "feetypes")
public class FeeType extends DatabaseItem {

    // Here are the codes
    public static String WEDDING_CENTER_FEE = "WEDDING_CENTER_FEE";
    public static String MEMORIAL_CENTER_FEE = "MEMORIAL_CENTER_FEE";
    public static String GIFT_CENTER_FEE = "GIFT_CENTER_FEE";
    public static String E_GIFT_CERT_FEE = "E_GIFT_CERT_FEE";
    public static String GIFT_CARD_FEE = "GIFT_CARD_FEE";
    public static String E_GIFT_CERT_BASE_FEE = "E_GIFT_CERT_BASE_FEE";
    public static String GIFT_CARD_BASE_FEE = "GIFT_CARD_BASE_FEE";
    public static String NOTE_CARD_FEE = "NOTE_CARD_FEE";
    public static String DOWNLOADED_GIFT_CERT_FEE = "DOWNLOADED_GIFT_CERT_FEE";

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "defaultamount")
    private BigDecimal defaultAmount;

    public FeeType() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getDefaultAmount() {
        return defaultAmount;
    }

    public void setDefaultAmount(BigDecimal defaultAmount) {
        this.defaultAmount = defaultAmount;
    }

    public boolean isGiftCardFeeType() {
        if (code.equals(E_GIFT_CERT_FEE)) return true;
        if (code.equals(GIFT_CARD_FEE)) return true;
        if (code.equals(E_GIFT_CERT_BASE_FEE)) return true;
        if (code.equals(GIFT_CARD_BASE_FEE)) return true;
        if (code.equals(DOWNLOADED_GIFT_CERT_FEE)) return true;

        return false;
    }

    public boolean isNoteCardFeeType() {
        if (code.equals(NOTE_CARD_FEE)) return true;

        return false;
    }
}
