import java.util.Scanner;
public class LetterGradeCalculator {

    public static void main(String[] args) {

        System.out.println("Hello! Welcome to the LetterGradeCalculator! Please input your grade as a number!\n\tType 'quit' to exit!\n");

        System.out.print("Enter your Grade: ");
        Scanner scanner = new Scanner(System.in);

        String grade = scanner.nextLine();

        while (!grade.equals("quit")) {
            double dbGrade;
            try {
                dbGrade = Double.parseDouble(grade);
                String letter = getLetterGrade(dbGrade);
                if (letter.equals("invalid")) {
                    System.out.println("Please enter a Grade between 0-100 inclusive.");
                } else {
                    System.out.println(String.format("Letter Grade is : %s", letter));
                }
            } catch (NumberFormatException e) {
                System.out.println(String.format("'%s' is not a valid number, please try again!", grade));
            } catch (NullPointerException e) {
                System.out.println("Grade somehow became null. Exiting...");
                return;
            }

            System.out.print("\nEnter your Grade: ");
            grade = scanner.nextLine();
        }
        scanner.close();
        System.out.println("Goodbye!");
    }

    private static String getLetterGrade(double grade) {
        if ((grade > 100.00) || (grade < 0.00)) {
            return "invalid";
        } else if (grade >= 90.00) {
            return "A";
        } else if (grade >= 80.00) {
            return "B";
        } else if (grade >= 70.00) {
            return "C";
        } else if (grade >= 60.00) {
            return "D";
        } else {
            return "F";
        }
    }
}
