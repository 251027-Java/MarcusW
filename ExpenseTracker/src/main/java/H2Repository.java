import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class H2Repository implements IRepository {

    private static final String H2_URL = "jdbc:h2:mem:expenses;DB_CLOSE_DELAY=-1"; //Final can be overwritten in constructor only
    private Connection connection;

    public H2Repository() {

        try{
            this.connection = DriverManager.getConnection(H2_URL);

            try ( Statement stmt = connection.createStatement() ) {
                String sql =
                        "CREATE SCHEMA IF NOT EXISTS ExpenseReport;" +
                                "CREATE TABLE IF NOT EXISTS ExpenseReport.Expenses ( " +
                                "id INTEGER PRIMARY KEY, " +
                                "date TIMESTAMP NOT NULL, " +
                                "price FLOAT CHECK (price > 0), " +
                                "merchant VARCHAR(50) NOT NULL" +
                                ");"; //NVARCHAR can handle Unicode characters
                stmt.execute(sql);
                System.out.println("Successful creation of H2 Database");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createExpense(Expense expense) {
        String sql = "INSERT INTO ExpenseReport.Expenses (id, date, price, merchant) " +
                "VALUES ( ?, ?, ?, ?);";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, expense.getId());
            stmt.setTimestamp(2, new java.sql.Timestamp(expense.getDate().getTime()));
            stmt.setDouble(3, expense.getValue());
            stmt.setString(4, expense.getMerchant());
            stmt.executeUpdate();
            System.out.println("Expense created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Expense readExpense(int id) {
        String sql =
                "SELECT * FROM ExpenseReport.Expenses WHERE id = ?;";
        try ( PreparedStatement stmt = connection.prepareStatement(sql) ) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            Expense expense = null;
            while (rs.next()) {
                int expense_id = rs.getInt("id"); // By column name
                java.util.Date date = rs.getTimestamp("date");
                double price = rs.getDouble("price");
                String merchant = rs.getString("merchant");
                expense = new Expense(expense_id, date, price, merchant);
            }
            return expense;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void updateExpense(Expense expense) {
        String sql =
                "UPDATE ExpenseReport.Expenses SET DATE=?, PRICE=?, MERCHANT=? WHERE ID=?;";
        try ( PreparedStatement stmt = connection.prepareStatement(sql) ) {
            stmt.setTimestamp(1, new java.sql.Timestamp(expense.getDate().getTime()));
            stmt.setDouble(2, expense.getValue());
            stmt.setString(3, expense.getMerchant());
            stmt.setInt(4, expense.getId());
            stmt.executeUpdate();
            System.out.println("Updated Successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteExpense(int id) {
        String sql =
                String.format("DELETE FROM ExpenseReport.Expenses WHERE ID = %d", id);
        try ( Statement stmt = connection.createStatement() ) {
            //stmt.setInt(1, id);
            stmt.executeUpdate(sql);
            System.out.println("Deleted Successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Expense> loadExpenses() {
        ArrayList<Expense> expenses = new ArrayList<>();

        String sql = "SELECT * FROM ExpenseReport.Expenses";

        try ( PreparedStatement stmt = connection.prepareStatement(sql) ) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int expense_id = rs.getInt("id"); // By column name
                java.util.Date date = rs.getTimestamp("date");
                double price = rs.getDouble("price");
                String merchant = rs.getString("merchant");
                expenses.add(new Expense(expense_id, date, price, merchant));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expenses;
    }

    @Override
    public void saveExpenses(List<Expense> expenses) {

    }
}
