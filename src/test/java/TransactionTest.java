import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TransactionTest {

    @Test
    public void test_EX_1() {
        Transaction transaction = new Transaction();
        transaction.createTransaction();
        transaction.insertTransaction();
        int total = countRecords();
        Assert.assertEquals(4, total);
    }

    @Test
    public void test_EX_2() {
        Transaction transaction = new Transaction();
        transaction.createTransaction();
        transaction.insertTransaction();
        transaction.insertTransaction();
        ArrayList<Person> people = transaction.readTransaction();
        Assert.assertEquals(8, people.size());
    }

    @Test
    public void test_EX_3() {
        Transaction transaction = new Transaction();
        transaction.createTransaction();
        transaction.insertTransaction();
        Assert.assertEquals(0, countWorkRecords());
    }

    private int countRecords() {
        int total = 0;
        try (Connection connection = DBConnection.getConnection(); Statement stmt = connection.createStatement()) {
            String sql = "SELECT COUNT(*) AS total FROM REGISTRATION";
            ResultSet count = stmt.executeQuery(sql);
            while(count.next()){
                total = count.getInt("total");
            }
            return total;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    private int countWorkRecords() {
        int total = 0;
        try (Connection connection = DBConnection.getConnection(); Statement stmt = connection.createStatement()) {
            String sql = "SELECT COUNT(*) AS total FROM WORK ";
            ResultSet count = stmt.executeQuery(sql);
            while(count.next()){
                total = count.getInt("total");
            }
            return total;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    @Test
    public void test_EX_4() {
        Transaction transaction = new Transaction();
        transaction.createTransaction();
        transaction.insertTransaction4();
        ArrayList<Person> people = transaction.readTransaction();
        Assert.assertEquals(4, people.size());
        Assert.assertEquals(1, countWorkRecords());
    }

    @Test
    public void test_EX_5() {
        Transaction transaction = new Transaction();
        MultiThread multiThread = new MultiThread();
        transaction.createTransaction();
        multiThread.runInsert();
        List<Person> people = multiThread.runRead();
        Assert.assertEquals(4, people.size());
        Assert.assertEquals(1, countWorkRecords());
    }

    @Test
    public void test_EX_6() {
        Transaction transaction = new Transaction();
        MultiThread multiThread = new MultiThread();
        transaction.createTransaction();
        multiThread.runInsert();
        List<Person> people = multiThread.runUpdate();
        List<Person> people2 = multiThread.runRead();
        Person changedPerson = people2.stream().filter(person -> "ChangedName".equals(person.getName())).findFirst().get();
        Assert.assertEquals(4, people2.size());
        Assert.assertEquals(people.get(0).getName(), changedPerson.getName());
        Assert.assertEquals(1, countWorkRecords());
    }
}
