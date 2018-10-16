package buildtools.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;
import javax.sql.DataSource;
import org.apache.commons.lang3.time.StopWatch;

public class ExecutionSQL{

    private SQLProfile profile;
    private DataSource dataSource;
    private boolean isTimeMetricsEnabled = false;
    private StopWatch watch = new StopWatch();

    public ExecutionSQL(SQLProfile profile, DataSource dataSource) {
        this.profile = profile;
        this.dataSource = dataSource;
    }

    public SQLProfile getProfiles() {
        return profile;
    }

    public void setProfiles(SQLProfile profile) {
        this.profile = profile;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public boolean isTimeMetricsEnabled() {
        return isTimeMetricsEnabled;
    }

    public void setTimeMetricsEnabled(boolean timeMetricsEnabled) {
        isTimeMetricsEnabled = timeMetricsEnabled;
    }

    public String  ExecuteSQLQuery(){
        String result = "";
        int count =0;
        if(isTimeMetricsEnabled()) watch.start();
        try(Connection conn = dataSource.getConnection();
            Statement stmt = conn.createStatement()){
            ResultSet rs = stmt.executeQuery("SELECT * FROM dbo.userprofile ");
            while(rs.next()){
                count++;
            }
            result = "Total records "+count;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        if (isTimeMetricsEnabled) {
            watch.stop();
            System.out.println("Thread ID - " + Thread.currentThread().getId() + " : " + watch.getTime(TimeUnit.SECONDS) + " Sec(s).");
        }
        return result;
    }
    @SuppressWarnings("Duplicates")
    public String ExecuteInsertQuery() {

        if (isTimeMetricsEnabled) watch.start();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO dbo.userprofile" +
                     " (id, username, city, country, mobile) VALUES (?, ?, ?, ?, ?)")) {

            pstmt.setLong(1, profile.getId());
            pstmt.setString(2, profile.getUserName());
            pstmt.setString(3, profile.getCity());
            pstmt.setString(4, profile.getCountry());
            pstmt.setLong(5, profile.getMobile());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (isTimeMetricsEnabled) {
            watch.stop();
            System.out.println("Thread ID - " + Thread.currentThread().getId() + " : " + watch.getTime(TimeUnit.MINUTES) + " Min(s).");
        }

        return "saaa"+Thread.currentThread().getId();
    }

    @Override
    public String toString() {
        return "ExecutionSQL{" +
                "profile=" + profile.toString() +
                '}';
    }
}
