import java.util.List;


public interface IRepository {

    public void createExpense(Expense expense);
    public Expense readExpense(int id);
    public void updateExpense(Expense expense);
    public void deleteExpense(int id);
    public List<Expense> loadExpenses();
    public void saveExpenses(List<Expense> expenses);
}
