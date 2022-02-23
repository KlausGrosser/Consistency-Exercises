import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class Transaction {

    public void createTransaction() {
        Connection connection = DBConnection.getConnection();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            DataManagement.sqlCreateTable(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBConnection.closeConnection(connection);
        }

    }

    public void insertTransaction() {
        Connection connection = DBConnection.getConnection();
        try (Statement stmt = connection.createStatement()) {
            DataManagement.insertDataIntoWorkTable(stmt);
            int recordAffected = DataManagement.insertDataIntoRegistrationTable(stmt);
            if (recordAffected < 5) {
                connection.rollback();
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Person> readTransaction() {

        ArrayList<Person> people = new ArrayList<>();
        try(Connection connection = DBConnection.getConnection();
            Statement stmt = connection.createStatement()) {
            people = DataManagement.getDataFromTable(stmt);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return people;
    }

    public void insertTransaction4() {
        Connection connection = DBConnection.getConnection();
        Savepoint savepoint = null;
        try {
            savepoint = connection.setSavepoint();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Statement stmt = connection.createStatement()) {
            DataManagement.insertDataIntoRegistrationTable(stmt);
            DataManagement.insertDataIntoWorkTable(stmt);
            savepoint = connection.setSavepoint();
            DataManagement.insertWrongDataIntoWorkTable(stmt);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback(savepoint);
                connection.commit();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void insertTransaction5() {
        try (Connection connection = DBConnection.getConnection();
             Statement stmt = connection.createStatement()) {
            DataManagement.insertDataIntoWorkTable(stmt);
            DataManagement.insertDataIntoRegistrationTable(stmt);
            sleep(2000);
            connection.commit();
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Person> readTransaction5() {
        ArrayList<Person> people = new ArrayList<>();
        try(Connection connection = DBConnection.getConnection();
            Statement stmt = connection.createStatement()) {
            sleep(100);
            people = DataManagement.getDataFromTable(stmt);
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        return people;
    }

    public ArrayList<Person> updateTransaction() {
        ArrayList<Person> people = new ArrayList<>();
        String sql = "SELECT id, name, lastName, age FROM Registration where id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            stmt.setInt(1, 1);
            people = DataManagement.getDataForUpdate(stmt);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return people;
    }
}

