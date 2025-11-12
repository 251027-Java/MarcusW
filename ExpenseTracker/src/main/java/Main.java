import java.util.*;

// As a user, I want to track my expenses so that I can build/submit an expense report at the end of the week.
// As a user, I need to include the date, value, and merchant to include on my expense report.

public class Main {
    // fields

    public static Scanner scan = new Scanner(System.in);
    public static Map<Integer, String> options = new LinkedHashMap<>(Map.of(1, "Create Expense", 2, "View Expenses", 3, "Update Expense", 4, "Remove Expense", 5, "Sum Expenses", 6, "Exit"));

    // methods
    static void main() {
        System.out.println("Expense Tracker Starting...");
        //IRepository repo = new H2Repository();
        IRepository repo = new MongoDBRepository();
        ExpenseService service = new ExpenseService(repo);
        //doDefault(service);


        Expense expense = service.createNewExpense(4, 500, "Target");
        expense = service.createNewExpense(5, 600, "Walmart");
        expense = service.createNewExpense(6, 12.65, "Whataburger");

        System.out.println(repo.readExpense(4));

        System.out.println("Updated Expense 4: " + service.updateExpense(new Expense(4, new Date(), 515, "Target")));
        System.out.println(repo.readExpense(4));
        System.out.println("Updated Expense 200: " + service.updateExpense(new Expense(200, new Date(), 505, "Target")));

        System.out.println("Delete expense 6: " + service.deleteExpense(6));
        System.out.println("Delete expense 200: " + service.deleteExpense(200));

        System.out.println("Printing:");
        service.printExpenses();

//        System.out.println("Updating:");
//        System.out.println(service.updateExpense(new Expense(5, new Date(),599.99, "Walmart")));
//        System.out.println(repo.readExpense(5));

//        System.out.println("Deleting:");
//        System.out.println(service.deleteExpense(5));
//        System.out.println(service.deleteExpense(5));




        //System.out.println(service.sumExpenses());
        //service.printExpenses();
        //user(service);
    }

    private static void doDefault(ExpenseService service) {
        System.out.println("Starting Default implementation");

        System.out.println("Creating Expenses");
        Expense expense = service.createNewExpense(4, 500, "Target");
        expense = service.createNewExpense(5, 12.65, "Whataburger");

        System.out.println("Reading Expenses");
        service.printExpenses();

        System.out.println("Updating Expense");
        Expense newExpense = new Expense(4, new Date(), 505.45, "Target");
        service.updateExpense(newExpense);
        service.printExpenses();

        System.out.println("Deleting Expenses");
        System.out.println("Expense deleted: " + service.deleteExpense(4));
        System.out.println("Expense deleted: " + service.deleteExpense(5));

        service.printExpenses();
    }

    private static void user(ExpenseService service) {
        int option = 0;
        do {
            option = getUserOption();
            if (option == 1) {
                createUserExpense(service);
            } else if (option == 2){
                System.out.println();
                service.printExpenses();
                System.out.println();
            } else if (option == 3) {
                System.out.println("TBI");
            } else if (option == 4) {
                System.out.println("TBI");
            } else if (option == 5) {
                System.out.println(service.sumExpenses());
            }
        } while (option != 6);
    }

    private static int getUserOption() {

        int option;
        while (true) {
            System.out.println("Repository Options: ");
            for (Map.Entry<Integer, String> entry : options.entrySet()) {
                System.out.println("\t" + entry.getKey() + " : " + entry.getValue());
            }
            System.out.print("Please enter a number option from above (1-" + String.valueOf(options.size()) + "): ");
            String input = scan.nextLine().strip();
            try {
                option = Integer.parseInt(input);
                if (!options.containsKey(option)) {
                    System.out.println("Invalid option. Please try again.");
                } else {
                    return option;
                }
            } catch (Exception e) {
                System.out.println("Not a valid number. Please try again");
            }
        }
    }

    private static void createUserExpense(ExpenseService service) {
        // get all input for a single expense
        int id = getUserExpenseId();
        double value = getUserExpenseValue();
        String merchant = getUserExpenseMerchant();
        service.createNewExpense(id, value, merchant);
    }

    private static int getUserExpenseId() {
        // get input for expense Id
        int id = 0;
        while (true) {
            System.out.print("Enter Expense ID: ");
            String input = scan.nextLine().strip();
            try {
                id = Integer.parseInt(input);
                return id;
            } catch (Exception e) {
                System.out.println("Invalid ID, please try again");
            }
        }
    }

    private static double getUserExpenseValue() {
        // get input for expense value
        double value = 0;
        while (true) {
            System.out.print("Enter Expense Value: ");
            String input = scan.nextLine().strip();
            try {
                value = Double.parseDouble(input);
                return value;
            } catch (Exception e) {
                System.out.println("Invalid Value, please try again");
            }
        }
    }

    private static String getUserExpenseMerchant() {
        // get input for expense merchant
        String merchant = "";
        while (true) {
            System.out.print("Enter Expense Merchant: ");
            merchant = scan.nextLine().strip();
            if (merchant.equalsIgnoreCase("")) {
                System.out.println("Invalid Merchant, please try again");
            } else {
                return merchant;
            }
        }
    }
}
