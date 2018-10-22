package com.hp.buildtools.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DBMonitoring {

    public static ScheduledExecutorService startDBMonitoring(String pool1, String pool2){
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    //To monitor the database connections utilization by threads.
        scheduledExecutorService.scheduleWithFixedDelay (()->
                DataSourceInformation.getDBMonitoringInfo(pool1),0 , 5,TimeUnit.SECONDS );
        scheduledExecutorService.scheduleWithFixedDelay (()->
                DataSourceInformation.getDBMonitoringInfo(pool2),0 , 5,TimeUnit.SECONDS );
        return scheduledExecutorService;
    }
}
