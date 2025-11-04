import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TextRepository implements IRepository {

    private final String filename = "expenses.txt";

    public TextRepository() {}

    @Override
    public List<Expense> loadExpenses() {

        List<Expense> expenses = new ArrayList<>();
        try (FileReader reader = new FileReader(this.filename)) {
            String file = reader.readAllAsString();
            String[] commaSplit = file.split(",");
            int id = 0;
            Date date = new Date();
            double value = 0;
            String merchant = "";
            int i = 0;
            for (String commas : commaSplit) {
                String sub = commas.split("=")[1];
                if (i == 0) {
                    id = Integer.parseInt(sub.strip());
                } else if (i == 1) {
                    date = new Date(sub.strip());
                } else if (i == 2) {
                    value = Double.parseDouble(sub.strip());
                } else {
                    merchant = sub.replace("]", "").strip();
                }
                i++;
                if (i == 4) {
                    i = 0;
                    expenses.add(new Expense(id, date, value, merchant));
                }
            }
        } catch (IOException e) {
            System.out.println("Whoops");
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
