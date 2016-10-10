package org.justgive.model;

/**
 * The OrderStatus is used to distinguish between provisional ("Active") and processed ("Completed")
 * records in the transaction table.  It also has several other states.
 *
 * @author Curtis, Jeff
 * @since October 2014
 */
public enum OrderStatus {
    Unknown("Unknown"),
    Completed("Completed"),
    Cancelled("Cancelled"),
    Active("Active"),
    Saved("Saved"),
    Deleted("Deleted"),
    Failed("Failed");

    private String name;

    private OrderStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
