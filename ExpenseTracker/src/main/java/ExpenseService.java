import java.util.*;

public class ExpenseService {
    //Fields
    private final IRepository repo;

    //Constructor
    public ExpenseService(IRepository repository) {
        this.repo = repository;
    }

    //Methods
    public Expense createNewExpense(int id, double value, String merchant) {
        if (repo.readExpense(id) != null) {
            return null;
        }
        Expense newExpense = new Expense(id, new Date(), value, merchant);
        repo.createExpense(newExpense);
        return newExpense;
    }

    public boolean deleteExpense(int id) {
        if (repo.readExpense(id) == null) {
            return false;
        }
        repo.deleteExpense(id);
        return true;
    }

    public boolean updateExpense(Expense expense) {
        if (repo.readExpense(expense.getId()) == null) {
            return false;
        }
        repo.updateExpense(expense);
        return true;
    }

    public void printExpenses() {
        List<Expense> expenses = repo.loadExpenses();
        for (Expense expense : expenses) {
            System.out.println(expense);
        }
    }

    public double sumExpenses() {
        List<Expense> expenses = repo.loadExpenses();
        double sum = 0;
        for (Expense e : expenses) {
            sum += e.getValue();
        }
        return sum;
    }
}
