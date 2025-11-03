import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TextRepository implements IRepository {

    private String filename = "expenses.txt";

    public TextRepository() {}

    @Override
    public List<Expense> readExpenses() {
        ArrayList<Expense> expenses = new ArrayList<>();
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
