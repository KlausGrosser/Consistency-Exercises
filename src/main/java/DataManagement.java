import java.sql.*;
import java.util.ArrayList;

public class DataManagement {

    public static void sqlCreateTable(Statement statement) throws SQLException {
        dropTable(statement);
        String sql = "CREATE TABLE IF NOT EXISTS REGISTRATION " +
                "(id IDENTITY NOT NULL PRIMARY KEY, " +
                " name VARCHAR(255), " +
                " lastName VARCHAR(255), " +
                " age INTEGER)";
        statement.executeUpdate(sql);

        sql = "CREATE TABLE IF NOT EXISTS WORK " +
                "(work_id IDENTITY NOT NULL PRIMARY KEY, " +
                " name VARCHAR(255), " +
                " difficulty INTEGER)";
        statement.executeUpdate(sql);
    }

    public static int insertDataIntoRegistrationTable(Statement statement) throws SQLException {
        String sql = "INSERT INTO registration " +
                "VALUES " +
                "(default, 'Adam','Falon','30')," +
                "(default, 'Mary','Gold','36')," +
                "(default, 'Adam','Currie','25')," +
                "(default, 'Mary','Jhonson','29')";
        return statement.executeUpdate(sql);
    }

    public static void insertDataIntoWorkTable(Statement statement) throws SQLException {
        String sql = "INSERT INTO work VALUES (default, 'Hard Work', 20)";
        statement.executeUpdate(sql);
    }

    public static void insertWrongDataIntoWorkTable(Statement statement) throws SQLException {
        String sql = "INSERT INTO work VALUES (default, 'Hard Work', 'Wrong')";
        statement.executeUpdate(sql);
    }

    private static void dropTable(Statement statement) throws SQLException {
        String sql = "DROP TABLE IF EXISTS REGISTRATION;" +
                "DROP TABLE IF EXISTS WORK";
        statement.executeUpdate(sql);
    }

    public static ArrayList<Person> getDataFromTable(Statement statement) throws SQLException {
        String sql = "SELECT id, name, lastName, age FROM registration";
        ResultSet rs = statement.executeQuery(sql);

        ArrayList<Person> peopleList = new ArrayList<>();

        while(rs.next()) {
            int id = rs.getInt("id");
            String first = rs.getString("name");
            String last = rs.getString("lastName");
            int age = rs.getInt("age");

            peopleList.add(new Person(id, first, last, age));
        }

        rs.close();

        return peopleList;
    }

    public static ArrayList<Person> getDataForUpdate(PreparedStatement statement) throws SQLException {

        ResultSet rs = statement.executeQuery();
        ArrayList<Person> peopleList = new ArrayList<>();
        while(rs.next()) {
            int id  = rs.getInt("id");
            int age = rs.getInt("age");
            rs.updateString("name", "ChangedName");
            rs.updateRow();
            String first = rs.getString("name");
            String last = rs.getString("lastName");

            peopleList.add(new Person(id, first, last, age));
        }

        rs.close();

        return peopleList;
    }


}
