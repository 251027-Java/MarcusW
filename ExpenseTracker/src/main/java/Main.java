import java.util.Date;
import java.util.List;
import java.util.ArrayList;

// As a user, I want to track my expenses so that I can build/submit an expense report at the end of the week.
// As a user, I need to include the date, value, and merchant to include on my expense report.

public class Main {
    // fields

    // methods
    static void main() {
        System.out.println("Expense Tracker Starting...");
        List<Expense> expenses = new ArrayList<>();

        System.out.println("Creating a test expense:");
        expenses.add(new Expense(1, new Date(), 99.95, "Walmart"));
        expenses.add(new Expense(2, new Date(), 85.75, "Costco"));
        expenses.add(new Expense(3, new Date(), 10000, "Private Jet"));
        expenses.add(new Expense(4, new Date(), 500, "Target"));
        expenses.add(new Expense(5, new Date(), 12.65, "Whataburger"));

        System.out.println(expenses);

        IRepository repo = new JSONRepository();
        //IRepository repo = new TextRepository();
        //IRepository repo = new CSVRepository();

        System.out.println("Writing expenses to file");
        repo.saveExpenses(expenses);

        System.out.println("Reading expenses from file");
        List<Expense> newExpenses = repo.loadExpenses();
        System.out.println(newExpenses);

        System.out.println("Expense Tracker Closing...");
    }
}
