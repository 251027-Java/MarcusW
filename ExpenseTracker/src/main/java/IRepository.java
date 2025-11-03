import java.util.List;


public interface IRepository {

    List<Expense> readExpenses();
    public void saveExpenses(List<Expense> expenses);
}
