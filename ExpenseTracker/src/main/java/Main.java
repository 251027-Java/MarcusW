import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

// As a user, I want to track my expenses so that I can build/submit an expense report at the end of the week.
// As a user, I need to include the date, value, and merchant to include on my expense report.

public class Main {
    // fields

    public static boolean writeExpensesToFileCSV(List<Expense> expenses, String filepath) {
        try (FileWriter writer = new FileWriter(filepath)) {
            writer.write("id,date,value,merchant\n");
            for (Expense expense : expenses) {
                writer.write(expense.toCsv() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Trouble writing to : " + filepath);
            return false;
        }
        return true;
    }

    public static boolean writeExpensesToFileJSON(List<Expense> expenses, String filepath) {
        try (FileWriter writer = new FileWriter(filepath)) {
            Gson gson = new Gson();
            gson.toJson(expenses, writer);
        } catch (IOException e) {
            System.out.println("Trouble writing to : " + filepath);
            return false;
        }
        return true;
    }

    public static ArrayList<Expense> readExpensesFromFileCSV(String filepath) throws IOException {
        ArrayList<Expense> expenses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line = reader.readLine();
            String[] values;
            while ((line = reader.readLine()) != null) {
                values = line.split(",");
                expenses.add(new Expense(Integer.parseInt(values[0]), new Date(values[1]), Double.parseDouble(values[2]), String.valueOf(values[3])));
            }
        }
        return expenses;
    }

    public static ArrayList<Expense> readExpensesFromFileJSON(String filepath) throws IOException {
        ArrayList<Expense> expenses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Expense>>() {}.getType();
            expenses = gson.fromJson(reader, listType);
        }
        return expenses;
    }

    // methods
    static void main() {
        System.out.println("Expense Tracker Starting...");
        List<Expense> expenses = new ArrayList<>();

        System.out.println("Creating a test expense:");
        expenses.add(new Expense(1, new Date(), 99.95, "Walmart"));
        expenses.add(new Expense(2, new Date(), 85.75, "Costco"));
        expenses.add(new Expense(3, new Date(), 10000, "Private Jet"));

        System.out.println(expenses);

        System.out.println("Writing expenses to file");

        String filepath = "./expenses.csv";
        boolean didWrite = writeExpensesToFileJSON(expenses, filepath);
        if (didWrite) {
            System.out.println("Wrote to file successfully!");
        } else { System.out.println("Uh oh something happened"); return; }

        System.out.println("Trying to read expenses from file");
        ArrayList<Expense> newExpenses;
        try {
            newExpenses = readExpensesFromFileJSON(filepath);
        } catch (IOException e) {
            System.out.println("Error reading from file");
            return;
        }

        System.out.println(newExpenses);

        System.out.println("Expense Tracker Closing...");
    }
}
