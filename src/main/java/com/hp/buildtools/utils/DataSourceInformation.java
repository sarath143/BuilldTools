package com.hp.buildtools.utils;

import com.zaxxer.hikari.HikariPoolMXBean;
import java.lang.management.ManagementFactory;
import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

public class DataSourceInformation {

    public static void getDBMonitoringInfo(String poolName){
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName pool = null;
        try {
            pool = new ObjectName("com.zaxxer.hikari:type=Pool ("+poolName+")");
        } catch (MalformedObjectNameException e) {
            e.printStackTrace();
        }
        HikariPoolMXBean poolProxy = JMX.newMBeanProxy(mBeanServer, pool, HikariPoolMXBean.class);

        int totalConnections  = poolProxy.getTotalConnections();
        int idleConnections = poolProxy.getIdleConnections();
        int activeConnections = poolProxy.getActiveConnections();
        int waitingConnections =  poolProxy.getThreadsAwaitingConnection();

        System.out.println("["+poolName+"]Total Connections :"+totalConnections+", " +
                "Active Connections :"+activeConnections+", Idle Connections :"+idleConnections+", No.of.Threads Waiting for Connections :"+waitingConnections);
    }
}
