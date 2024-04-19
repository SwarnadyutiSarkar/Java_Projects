import java.util.Scanner;

public class GradingSystem {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Grading System");

        System.out.print("Enter the total marks: ");
        int totalMarks = scanner.nextInt();

        System.out.print("Enter the marks obtained: ");
        int marksObtained = scanner.nextInt();

        char grade = calculateGrade(totalMarks, marksObtained);

        System.out.println("Grade: " + grade);

        scanner.close();
    }

    private static char calculateGrade(int totalMarks, int marksObtained) {
        double percentage = (double) marksObtained / totalMarks * 100;

        if (percentage >= 90) {
            return 'A';
        } else if (percentage >= 80) {
            return 'B';
        } else if (percentage >= 70) {
            return 'C';
        } else if (percentage >= 60) {
            return 'D';
        } else if (percentage >= 50) {
            return 'E';
        } else {
            return 'F';
        }
    }
}
