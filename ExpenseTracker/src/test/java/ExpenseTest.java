import java.util.Date;

public class ExpenseTest {

    public static void main(String[] args) {
        testExpenseCreation();
    }

    public static void testExpenseCreation() {
        // Arrange conditions for the test
        Expense expense = new Expense(1, new Date(), 100, "DummyMerchant");

        // Act - what functionality are you trying to validate
        int possibleId = expense.getId();
        double possibleValue = expense.getValue();

        // Assert - compare the expected to the actual
        if (possibleId != 1) {System.out.println("Failed id");}
        else if (possibleValue != 100) {System.out.println("Failed value");}
        else {System.out.println("Passed Expense Creation!!");}
    }
}
