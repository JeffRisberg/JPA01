package org.justgive.model;

import java.util.HashMap;
import java.util.Map;

/**
 * An entity mapped to the former charitysources table.
 * <p/>
 * <p/>
 * sourceid |   sourcename
 * ----------+-----------------
 * 2 | GuideStar
 * 12 | IRS
 * 4 | Obsolete
 * 10 | Vendor
 * 13 | IRS 990-N
 * 14 | CRA
 * 16 | Private Schools
 * 15 | Public Schools
 */
public enum CharitySource {
    Guidestar(2, "Guidestar", true),
    IRS(12, "IRS", true),
    IRS_990_N(13, "IRS 990-N", true),
    Vendor(10, "Vendor", true),
    CRA(14, "CRA", true),
    Public_Schools(15, "Public Schools", true),
    Private_Schools(16, "Private Schools", true),
    Obsolete(4, "Obsolete", false);

    private int id;
    private String label;
    private boolean searchable;

    /**
     * Constructor
     */
    CharitySource(int id, String label, boolean searchable) {
        this.id = id;
        this.label = label;
        this.searchable = searchable;

        ObjectRepo.idMap.put(id, this);
        ObjectRepo.nameMap.put(this.name(), this);
    }

    public int getId() {
        return id;
    }

    public String getSourceName() {
        return label;
    }

    public boolean isSearchable() {
        return searchable;
    }

    public static CharitySource getById(Integer id) {
        return ObjectRepo.idMap.get(id);
    }

    public static CharitySource getByName(String name) {
        return ObjectRepo.nameMap.get(name);
    }

    public static Map<Integer, CharitySource> getIdMap() {
        return ObjectRepo.idMap;
    }

    public static Map<String, CharitySource> getNameMap() {
        return ObjectRepo.nameMap;
    }

    static protected class ObjectRepo {
        static protected Map<Integer, CharitySource> idMap = new HashMap<>();
        static protected Map<String, CharitySource> nameMap = new HashMap<>();
    }
}
