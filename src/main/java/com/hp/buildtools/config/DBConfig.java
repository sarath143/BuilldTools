package com.hp.buildtools.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;

public class DBConfig {

    public static DataSource getDataSource1(String poolName) {
        return createDataSource(poolName, "com.microsoft.sqlserver.jdbc.SQLServerDataSource",
                "mytestdb","mytestuser", "Test@123!", 10, 4);
    }

    public static DataSource getDataSource2(String poolName) {
        return createDataSource(poolName, "com.microsoft.sqlserver.jdbc.SQLServerDataSource",
                "mytestdb1","mytestuser", "Test@123!", 10, 4);
    }

    private static DataSource createDataSource( String poolName, String dataSourceClassName, String catalog,
                                         String userName, String password, int maxPoolSize, int minIdle){
        HikariConfig config = new HikariConfig();
        config.setPoolName(poolName);
        config.setDataSourceClassName(dataSourceClassName);
        config.setCatalog(catalog);
        config.setUsername(userName);
        config.setPassword(password);
        config.setMaximumPoolSize(maxPoolSize);
        config.setMinimumIdle(minIdle);
        config.setAutoCommit(true);
        config.setRegisterMbeans(true); //To Monitor connection pooling for active, idle, waiting.
        DataSource dataSource = new HikariDataSource(config);
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSource;
    }


}
