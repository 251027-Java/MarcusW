import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CSVRepository implements IRepository {

    private final String filename = "expenses.csv";

    public CSVRepository() {}

    @Override
    public void createExpense(Expense expense) {
        List<Expense> expenses = this.loadExpenses();
        expenses.add(expense);
        this.saveExpenses(expenses);
    }

    @Override
    public Expense readExpense(int id) {
        return loadExpenses().stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void updateExpense(Expense expense) {
        List<Expense> expenses = loadExpenses();
        List<Expense> updatedExpenses = expenses.stream()
                .map(e -> (e.getId() == expense.getId()) ? expense : e)
                .collect(Collectors.toList());
        saveExpenses(updatedExpenses);
    }

    @Override
    public void deleteExpense(int id) {
        List<Expense> expenses = loadExpenses();
        expenses.removeIf(e -> e.getId() == id);
        saveExpenses(expenses);
    }

    @Override
    public List<Expense> loadExpenses() {
        List<Expense> expenses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(this.filename))) {
            String line = reader.readLine(); // don't care about header
            String[] values;
            while ((line = reader.readLine()) != null) {
                values = line.split(",");
                expenses.add(new Expense(Integer.parseInt(values[0]), new Date(values[1]), Double.parseDouble(values[2]), String.valueOf(values[3])));
            }
        } catch (IOException e) {
            System.out.println("Whoops");
        } catch (Exception e) {
            System.out.println("Uh oh");
        }
        return expenses;
    }

    @Override
    public void saveExpenses(List<Expense> expenses) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.filename))) {
            writer.write("id,date,value,merchant\n");
            for (Expense expense : expenses) {
                writer.write(expense.toCsv() + "\n");
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println("Trouble writing to : " + this.filename);
        }
    }
}
