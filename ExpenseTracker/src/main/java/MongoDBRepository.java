import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import com.mongodb.client.*;
import com.mongodb.MongoClientSettings;
import com.mongodb.ConnectionString;
import com.mongodb.MongoWriteException;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

public class MongoDBRepository implements IRepository {

    private MongoClient mongoClient;
    private String MONGO_USERNAME = "your_username";
    private String MONGO_PW = "your_password";
    private String MONGO_DBNAME = "expensedb";
    private String MONGO_CONAME = "expenses";
    private MongoDatabase database;
    MongoCollection<Document> collection;

    public MongoDBRepository() {
        this.mongoClient = MongoClients.create(MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString("mongodb://" + this.MONGO_USERNAME + ":" + this.MONGO_PW + "@localhost:27017/"))
                .build());

        this.database = mongoClient.getDatabase(this.MONGO_DBNAME);
        this.collection = database.getCollection(this.MONGO_CONAME);
    }

    @Override
    public void createExpense(Expense expense) {

        try {
            Document doc = new Document("_id", expense.getId())
                    .append("date", String.valueOf(expense.getDate()))
                    .append("value", expense.getValue())
                    .append("merchant", expense.getMerchant());
            InsertOneResult result = collection.insertOne(doc);
        } catch (MongoWriteException e) {
            System.out.println("Unable to insert Expense: " + expense.getId());
        }
    }

    @Override
    public Expense readExpense(int id) {

        Document document = collection.find(Filters.eq("_id", id)).first();

        if (document == null) {
            return null;
        }

        return new Expense(id, new Date(String.valueOf(document.get("date"))), (double) document.get("value"), String.valueOf(document.get("merchant")));
    }

    @Override
    public void updateExpense(Expense expense) {
        try {
            Bson filter = Filters.eq("_id", expense.getId());

            Bson update = Updates.combine(
                Updates.set("date", String.valueOf(expense.getDate())),
                Updates.set("value", expense.getValue()),
                Updates.set("merchant", expense.getMerchant()));
            UpdateResult result = collection.updateOne(filter, update);
        } catch (Exception e) {
            System.out.println("Failed to update Expense: " + expense.getId());
            e.printStackTrace();
        }
    }

    @Override
    public void deleteExpense(int id) {
        try {
            Bson filter = Filters.eq("_id", id);
            Object obj = this.collection.findOneAndDelete(filter);
        } catch (Exception e) {
            System.out.println("Couldn't delete Expense: " + id);
            e.printStackTrace();
        }
    }

    @Override
    public List<Expense> loadExpenses() {
        List<Expense> expenses = new ArrayList<>();
        FindIterable<Document> documents = this.collection.find();

        for (Document doc : documents) {
            expenses.add(new Expense(
                    (int) doc.get("_id"),
                    new Date(String.valueOf(doc.get("date"))),
                    (double) doc.get("value"),
                    String.valueOf(doc.get("merchant"))));
        }

        return expenses;
    }

    @Override
    public void saveExpenses(List<Expense> expenses) {

    }
}
