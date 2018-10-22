package com.hp.buildtools.service2;

import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

public class SQLQueryProfile {
    private final String SqlQuery;
    private List<String> queryResultsDiff;
    private boolean isQueryResultsSame;

    public SQLQueryProfile(String sqlQuery) {
        SqlQuery = sqlQuery;
    }

    public String getSqlQuery() {
        return SqlQuery;
    }

    public List<String> getQueryResultsDiff() {
        return queryResultsDiff;
    }

    public void setQueryResultsDiff(List<String> queryResultsDiff) {
        this.queryResultsDiff = queryResultsDiff;
    }

    public boolean isQueryResultsSame() {
        return isQueryResultsSame;
    }

    public void setQueryResultsSame(boolean queryResultsSame) {
        isQueryResultsSame = queryResultsSame;
    }

    @Override
    public String toString() {
        return "SQLQueryProfile{" +
                "SqlQuery='" + SqlQuery + '\'' +
                ", queryResultsDiff=" + queryResultsDiff +
                ", isQueryResultsSame=" + isQueryResultsSame +
                '}';
    }
}
