package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnUtil {

    public static Connection getMySQLConnection() throws ClassNotFoundException, SQLException {
        String hostName = "127.0.0.1";
        String dbName = "my_db";
        String userName = "bestuser";
        String password = "bestuser";
        return getMySQLConnection(hostName, dbName, userName, password);
    }

    private static Connection getMySQLConnection(String hostName, String dbName, String userName, String password) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        String connectionURL = "jdbc:mysql://127.0.0.1:3306/my_db";
        Connection connection = DriverManager.getConnection(connectionURL, userName, password);

        return connection;
    }
}
