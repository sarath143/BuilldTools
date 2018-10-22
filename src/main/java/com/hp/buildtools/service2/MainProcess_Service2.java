package com.hp.buildtools.service2;

import com.hp.buildtools.config.DBConfig;
import com.hp.buildtools.utils.DBMonitoring;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.sql.DataSource;
import org.apache.commons.lang3.time.StopWatch;

public class MainProcess_Service2 {
    private static final String PoolNameForDatabase1 = "Pool-1";
    private static final String PoolNameForDatabase2 = "Pool-2";
    private static final DataSource dataSource1 = DBConfig.getDataSource1(PoolNameForDatabase1);
    private static final DataSource dataSource2 = DBConfig.getDataSource2(PoolNameForDatabase2);

    public static void main(String args[]) throws IOException {
        DBMonitoring.startDBMonitoring(PoolNameForDatabase1, PoolNameForDatabase2);
        String  Queriesfile = "C:\\Users\\C52816A\\Desktop\\ftest.txt";
        StopWatch watch = new StopWatch();
        watch.start();

//        List<String> fileContent = Files.readAllLines(Queriesfile);
      //  Scanner fileReader = new Scanner(new File(Queriesfile));
        Files.
            lines(Paths.get(Queriesfile))
            .parallel()
            .map(line -> new SQLOperations(dataSource1, dataSource2, line))
            .map(SQLOperations::executeQuery)
            .filter(SQLQueryProfile::isQueryResultsSame)
            .map(SQLQueryProfile::getSqlQuery).
            forEach(System.out::println);
        watch.stop();
        System.out.println("Total Time : "+watch.getTime(TimeUnit.SECONDS)+" Sec(s)");
    }
}