package buildtools.service;

import com.hp.buildtools.config.DBConfig;
import com.hp.buildtools.utils.DBMonitoring;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.sql.DataSource;
import org.apache.commons.lang3.time.StopWatch;

public class MainProcess_Sol2 {
    private static DataSource dataSource1;
    private static DataSource dataSource2;
    private static ExecutorService service;
    private static List<SQLProfile> profiles;
    private static AtomicLong countIncrement = new AtomicLong();

    public static void main(String args[]) {
        dataSource1 = DBConfig.getDataSource1();
        dataSource2 = DBConfig.getDataSource2();
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        //To monitor the database connections utilization by threads.
        scheduledExecutorService.scheduleWithFixedDelay (()->DBMonitoring.getDBMonitoringInfo("Pool-1"),0 , 5,TimeUnit.SECONDS );
        scheduledExecutorService.scheduleWithFixedDelay (()->DBMonitoring.getDBMonitoringInfo("Pool-2"),0 , 5,TimeUnit.SECONDS );

        Predicate<SQLProfile> isDBSQLQuery_Results_Same = sqlProfile -> {
            ExecutionSQL queryDB1 = new ExecutionSQL(sqlProfile, dataSource1 );
            ExecutionSQL queryDB2 = new ExecutionSQL(sqlProfile, dataSource2 );
            List<String> results = Stream.of(queryDB1, queryDB2).map(query -> query.ExecuteInsertQuery())
               //     .reduce(true, (result1, result2)-> { bolean result = result1.equals(result2)}, )
                    .collect(Collectors.toList());

            return results.get(0).equals(results.get(1));

        };


        List<SQLProfile> profiles = generateUserProfiles();
        StopWatch watch = new StopWatch();
        watch.start();
        profiles.parallelStream().filter(isDBSQLQuery_Results_Same).forEach(profile -> profile.getId());
        watch.stop();
        System.out.println("Total time taken "+watch.getTime(TimeUnit.SECONDS));
      //  scheduledExecutorService.shutdown();




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


}
