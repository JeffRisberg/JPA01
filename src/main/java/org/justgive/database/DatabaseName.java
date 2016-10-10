package org.justgive.database;

/**
 * Created by petercowan on 2/22/16.
 */
public class DatabaseName {
    private String name;

    public DatabaseName() throws DBException {
        name = DBSessionFactory.getInstance().getDatabaseName();
    }

    public DatabaseName(String name) {
        this.name = name;
    }

    public boolean isQa() {
        return name.contains("qa");
    }

    public boolean isDev() {
        return name.contains("dev");
    }

    public boolean isProd() {
        return !name.contains("_");
    }

    public boolean isTrunk() {
        return name.contains("trunk");
    }

    public boolean isRb() {
        return name.contains("rb");
    }

    public String getEnv() {
        return (isDev()) ? "dev" : (isQa()) ? "qa" : (!isProd()) ? "prod" : "dev";
    }

    public String getBranch() {
        return (isTrunk()) ? "trunk" : (isRb()) ? "rb" : (!isProd()) ? "rb" : "trunk";
    }

    public String getName() {
        return name;
    }
}
