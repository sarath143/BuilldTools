package com.hp.buildtools.practice;

import com.hp.buildtools.config.DBConfig;
import com.hp.buildtools.service1.ExecutionSQL;
import com.hp.buildtools.service1.ExecutionSQL_Callable;
import com.hp.buildtools.service1.ExecutionTasks;
import com.hp.buildtools.service1.SQLProfile;
import com.hp.buildtools.utils.DataSourceInformation;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.apache.commons.lang3.time.StopWatch;

public class MainProcess_Sol1 {
    private static DataSource dataSource1;
    private static DataSource dataSource2;
    private static ExecutorService service;
    private static List<SQLProfile> profiles;
    private static AtomicLong countIncrement = new AtomicLong();

    public static void main(String args[]) {
        dataSource1 = DBConfig.getDataSource1("Pool-1");
        dataSource2 = DBConfig.getDataSource2("Pool-2");
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        //To monitor the database connections utilization by threads.
        scheduledExecutorService.scheduleWithFixedDelay (()-> DataSourceInformation.getDBMonitoringInfo("Pool-1"),0 , 5,TimeUnit.SECONDS );
        scheduledExecutorService.scheduleWithFixedDelay (()-> DataSourceInformation.getDBMonitoringInfo("Pool-2"),0 , 5,TimeUnit.SECONDS );

        //Get list of of profiles
        StopWatch watch = new StopWatch();
        watch.start();
        profiles = generateUserProfiles();
        profiles.stream().parallel().map(profile -> new ExecutionTasks(profile, dataSource1,dataSource2 ))
                .peek(task -> task.setTimeMetricsEnabled(true))
                .forEach(task -> task.ExecuteTask());
        scheduledExecutorService.shutdown();
        watch.stop();
        System.out.println("Total time:"+watch.getTime(TimeUnit.SECONDS));
      //  parallelProcess();

    }

    /**
     * Generate the list of user profiles
     * @return
     */
    public static List<SQLProfile> generateUserProfiles(){
        List<SQLProfile> SQLQueries = new ArrayList<>();
        for(long i=0; i<1000000; i++) {
            SQLQueries.add(getUserProfile());
        }
        return SQLQueries;
    }


    /**
     * Generate the UserProfile with some dummy values
     * @return
     */
    public static SQLProfile getUserProfile(){
        long userID = countIncrement.incrementAndGet();
        long mobile = userID*7;
        SQLProfile SQLProfile = new SQLProfile(userID, "User-"+userID, "CITY-"+userID, "COUNTRY-"+userID , mobile );
        return SQLProfile;
    }

    /**
     * Thr process for serially execute the tasks.
     */
    public static void SerialProcess(){
        StopWatch watch = new StopWatch();
        watch.start();
        profiles.stream().map(profile->new ExecutionSQL(profile, dataSource1)).forEach(executionSQL -> {
            executionSQL.setTimeMetricsEnabled(false);
            executionSQL.ExecuteInsertQuery();});
        watch.stop();
        System.out.println("Total time taken in sequential:"+watch.getTime(TimeUnit.SECONDS));
    }

    /**
     * The process of parallel execute the tasks.
     */
    public static void parallelProcess(){
        StopWatch watch = new StopWatch();
        watch.start();
        profiles = generateUserProfiles();
        profiles.stream().parallel().map(profile->new ExecutionSQL(profile, dataSource1)).forEach(executionSQL -> {
            executionSQL.setTimeMetricsEnabled(false);
            executionSQL.ExecuteInsertQuery();});
        watch.stop();
        System.out.println("Total time taken in parallel by Parallel Stream:"+watch.getTime(TimeUnit.SECONDS));
    }

    /**
     * The other way for parallel executing the tasks.
     */
    public static void ExecuteServiceProcess(){
        StopWatch watch = new StopWatch();
        watch.start();
        ExecutorService service = Executors.newFixedThreadPool(20);
        try {
            service.invokeAll(profiles.stream().map(profile -> new ExecutionSQL_Callable(profile, dataSource1)).collect(Collectors.toList()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.shutdown();
        watch.stop();
        System.out.println("Total time taken in parallel by ExecuteService :"+watch.getTime(TimeUnit.SECONDS));
    }
}
