package dao;

import connection.DBCPDataSource;
import model.Developer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeveloperDao {

    private DBCPDataSource dbcpDataSource;

    private static final String INSERT_DEVELOPER_SQL = "INSERT INTO developers" + "  (name, specialty, salary) VALUES " +
            " (?, ?, ?);";

    private static final String SELECT_DEVELOPER_BY_ID = "select id,name,specialty,salary from developers where id =?";
    private static final String SELECT_ALL_DEVELOPERS = "select * from developers";
    private static final String DELETE_DEVELOPERS_SQL = "delete from developers where id = ?;";
    private static final String UPDATE_DEVELOPERS_SQL = "update developers set name = ?,specialty= ?, salary =? where id = ?;";

    public DeveloperDao() {
    }

    public void insertDeveloper(Developer developer) throws SQLException {
        System.out.println(INSERT_DEVELOPER_SQL);
        // try-with-resource statement will auto close the connection.
        try (Connection connection = dbcpDataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_DEVELOPER_SQL)) {
            preparedStatement.setString(1, developer.getName());
            preparedStatement.setString(2, developer.getSpecialty());
            preparedStatement.setInt(3, developer.getSalary());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Developer selectDeveloper(int id) {
        Developer developer = null;
        // Step 1: Establishing a Connection
        try (Connection connection = dbcpDataSource.getConnection();
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_DEVELOPER_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                String name = rs.getString("name");
                String specialty = rs.getString("specialty");
                int salary = rs.getInt("salary");
                developer = new Developer(id, name, specialty, salary);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developer;
    }

    public List<Developer> selectAllDevelopers() {

        // using try-with-resources to avoid closing resources (boiler plate code)
        List<Developer> developers = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (Connection connection = dbcpDataSource.getConnection();
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_DEVELOPERS);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String specialty = rs.getString("specialty");
                int salary = rs.getInt("salary");
                developers.add(new Developer(id, name, specialty, salary));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return developers;
    }

    public boolean deleteDeveloper(int id) throws SQLException {
        boolean rowDeleted = false;
        try (Connection connection = dbcpDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_DEVELOPERS_SQL);) {
            statement.setInt(1, id);
            if (statement.executeUpdate() > 0) {
                rowDeleted = true;
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return rowDeleted;
    }

    public boolean updateDeveloper(Developer developer) throws SQLException {
        boolean rowUpdated = false;
        try (Connection connection = dbcpDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_DEVELOPERS_SQL);) {
            statement.setString(1, developer.getName());
            statement.setString(2, developer.getSpecialty());
            statement.setInt(3, developer.getSalary());
            statement.setInt(4, developer.getId());

            rowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return rowUpdated;
    }
}
