package buildtools.service;

import java.util.concurrent.CompletableFuture;
import javax.sql.DataSource;

public class ExecutionTasks {

    private SQLProfile SQLProfile;
    private DataSource dataSource1;
    private DataSource dataSource2;
    private boolean isTimeMetricsEnabled = false;

    /**
     *
     * @param SQLProfile
     * @param dataSource1
     * @param dataSource2
     */
    public ExecutionTasks(SQLProfile SQLProfile, DataSource dataSource1, DataSource dataSource2) {
        this.SQLProfile = SQLProfile;
        this.dataSource1 = dataSource1;
        this.dataSource2 = dataSource2;
    }

    public boolean isTimeMetricsEnabled() {
        return isTimeMetricsEnabled;
    }

    public void setTimeMetricsEnabled(boolean timeMetricsEnabled) {
        isTimeMetricsEnabled = timeMetricsEnabled;
    }

    public void ExecuteTask(){
        CompletableFuture<String> executionSQLForDB1 = CompletableFuture.supplyAsync(()->{
            ExecutionSQL executionSQL = new ExecutionSQL(SQLProfile, dataSource1);
            executionSQL.setTimeMetricsEnabled(isTimeMetricsEnabled);
            return executionSQL.ExecuteInsertQuery();
        });
        CompletableFuture<String> executionSQLForDB2 = CompletableFuture.supplyAsync(()->{
            ExecutionSQL executionSQL = new ExecutionSQL(SQLProfile, dataSource2);
            executionSQL.setTimeMetricsEnabled(isTimeMetricsEnabled);
            return executionSQL.ExecuteInsertQuery();
        });

        executionSQLForDB1.thenCombineAsync(executionSQLForDB2, String::equals )
                .thenAccept(result -> {
                    if(result);
//                        System.out.println("Log the file");
                });

       /* try {
            executionSQLForDB1.get();
            executionSQLForDB2.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }*/
    }
}
