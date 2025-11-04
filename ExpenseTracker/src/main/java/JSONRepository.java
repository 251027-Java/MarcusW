import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JSONRepository implements IRepository {

    private final String filename = "expenses.json";
    private final Gson gson = new Gson();

    public JSONRepository() {}

    @Override
    public List<Expense> loadExpenses() {
        List<Expense> expenses = null;
        try (FileReader reader = new FileReader(this.filename)) {
            Type listType = new TypeToken<List<Expense>>() {}.getType();
                // {} creates an Anonymous Inner Class which basically means that we are creating a subclass (child class) that extends TypeToken. i.e. public class AnonymousInnerClass extends TypeToken... The .getType() is an inherited function from the Object class! This means it's getting the meta information for List<Expense> which it will then use to read the json into the correct type.
            expenses = this.gson.fromJson(reader, listType);
        } catch (IOException e) {
            System.out.println("Whoops");
        }
        return (expenses != null) ? expenses : new ArrayList<>();
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
