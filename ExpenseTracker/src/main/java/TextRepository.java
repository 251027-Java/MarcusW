import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TextRepository implements IRepository {

    private final String filename = "expenses.txt";

    public TextRepository() {}

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
        try (FileReader reader = new FileReader(this.filename)) {
            String file = reader.readAllAsString();
            String[] commaSplits = file.split(",");
            int id = 0;
            Date date = new Date();
            double value = 0;
            String merchant = "";
            int i = 0;
            for (String splits : commaSplits) {
                String sub = splits.split("=")[1];
                if (i == 0) {
                    id = Integer.parseInt(sub.strip());
                } else if (i == 1) {
                    date = new Date(sub.strip()); //deprecated, but still works fine
                } else if (i == 2) {
                    value = Double.parseDouble(sub.strip());
                } else {
                    merchant = sub.replace("]", "").strip(); // need to remove trailing bracket
                }
                i++;
                if (i == 4) { //if we've read all parameters already, add Expense and reset.
                    i = 0;
                    expenses.add(new Expense(id, date, value, merchant));
                }
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
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(this.filename), true);
            writer.println(expenses.toString());
            writer.flush();
        } catch (IOException e) {
            System.out.println("Whoops");
        }
    }
}
