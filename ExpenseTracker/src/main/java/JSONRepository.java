import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JSONRepository implements IRepository {

    private String filename = "expenses.json";
    private Gson gson = new Gson();

    public JSONRepository() {}

    @Override
    public List<Expense> readExpenses() {
        ArrayList<Expense> expenses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(this.filename))) {
            Type listType = new TypeToken<ArrayList<Expense>>() {}.getType();
            expenses = this.gson.fromJson(reader, listType);
        } catch (Exception e) {
            System.out.println("Whoops");
        }
        return expenses;
    }

    @Override
    public void saveExpenses(List<Expense> expenses) {
        try (FileWriter writer = new FileWriter(this.filename)) {
            this.gson.toJson(expenses, writer);
        } catch (IOException e) {
            System.out.println("Trouble writing to : " + this.filename);
        }
    }
}
