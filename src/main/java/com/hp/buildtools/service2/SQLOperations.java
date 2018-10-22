package com.hp.buildtools.service2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import javax.sql.DataSource;

public class SQLOperations {
    private final DataSource dataSource1;
    private final DataSource dataSource2;
    private final String sqlQuery;


    public SQLOperations(final DataSource dataSource1, final DataSource dataSource2, final String sqlQuery) {
        this.dataSource1 = dataSource1;
        this.dataSource2 = dataSource2;
        this.sqlQuery = sqlQuery;
    }

    public SQLQueryProfile executeQuery() {
        Supplier<SQLQueryProfile> executeQuerySupplier = () -> {
            SQLQueryProfile sqlQueryProfile = new SQLQueryProfile(sqlQuery);
            try (Connection connection1 = dataSource1.getConnection();
                 Connection connection2 = dataSource2.getConnection();
                 Statement statement1 = connection1.createStatement();
                 Statement statement2 = connection2.createStatement();) {
                try (ResultSet resultSet1 = statement1.executeQuery(sqlQuery);
                     ResultSet resultSet2 = statement2.executeQuery(sqlQuery)) {
                    sqlQueryProfile.setQueryResultsDiff(compareTwoResultSets(resultSet1, resultSet2));
                    sqlQueryProfile.setQueryResultsSame(sqlQueryProfile.getQueryResultsDiff().size() > 0);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return sqlQueryProfile;
        };
        return executeQuerySupplier.get();
    }

    public List<String> compareTwoResultSets(ResultSet resultSet1, ResultSet resultSet2) {
        List<String> comparedResults = new ArrayList<>();
        try {
            int totalRecords_RS1 = getRowsCount(resultSet1);
            int totalRecords_RS2 = getRowsCount(resultSet2);
            int totalColsCount_RS1 = resultSet1.getMetaData().getColumnCount();
            int totalColsCount_RS2 = resultSet1.getMetaData().getColumnCount();
            if (totalRecords_RS1 != totalRecords_RS2 || totalColsCount_RS1 != totalColsCount_RS2)
                return comparedResults;
            else {
                while (resultSet1.next()) {
                    resultSet2.next();
                    for (int i = 1; i <= totalColsCount_RS1; i++) {
                        if (!resultSet1.getObject(i).equals(resultSet2.getObject(i))) {
                            comparedResults.add("[" + resultSet1.getObject(i) + "]" + "[" + resultSet2.getObject(i) + "]");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comparedResults;
    }

    public int getRowsCount(ResultSet resultSet) throws SQLException {
        int numRows = 0;
        resultSet.last(); //Set it to last row
        numRows = resultSet.getRow(); //Get the last row number
        resultSet.beforeFirst(); //Set it back to first row
        return numRows;
    }
}
