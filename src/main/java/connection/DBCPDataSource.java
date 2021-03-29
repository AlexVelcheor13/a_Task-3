package connection;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBCPDataSource {

    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        //ds.setDriverClassName("com.google.cloud.sql.mysql.SocketFactory");
        ds.setUrl("jdbc:mysql:///my_db?cloudSqlInstance=dynamic-market-308820:europe-central2:my-sql&socketFactory=com.google.cloud.sql.mysql.SocketFactory&user=root&password=andersen123");
        ds.setUsername("root");
        ds.setPassword("andersen123");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private DBCPDataSource(){ }
}
