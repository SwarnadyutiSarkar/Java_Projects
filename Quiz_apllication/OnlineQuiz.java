import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OnlineQuiz {
    private static Scanner scanner = new Scanner(System.in);
    private static List<Question> questions = new ArrayList<>();

    public static void main(String[] args) {
        initializeQuestions();

        System.out.println("Online Quiz Application - Intermediate Java Language");
        System.out.println("There are " + questions.size() + " questions. Good luck!");

        int score = 0;

        for (Question question : questions) {
            System.out.println("\n" + question.getQuestion());
            System.out.println("Options:");
            for (int i = 0; i < question.getOptions().size(); i++) {
                System.out.println((i + 1) + ". " + question.getOptions().get(i));
            }

            System.out.print("Enter your answer (1-" + question.getOptions().size() + "): ");
            int userAnswer = scanner.nextInt();

            if (userAnswer >= 1 && userAnswer <= question.getOptions().size() && 
                question.getOptions().get(userAnswer - 1).equals(question.getCorrectAnswer())) {
                score++;
                System.out.println("Correct!");
            } else {
                System.out.println("Incorrect! The correct answer is: " + question.getCorrectAnswer());
            }
        }

        System.out.println("\nQuiz completed! Your score is: " + score + "/" + questions.size());

        scanner.close();
    }

    private static void initializeQuestions() {
        questions.add(new Question("What is the correct way to declare a constant in Java?", 
                                   List.of("final int x = 10;", "const int x = 10;", "int x = 10;"), 
                                   "final int x = 10;"));
        
        questions.add(new Question("Which Java keyword is used for inheritance?", 
                                   List.of("extends", "implements", "inherit"), 
                                   "extends"));

        questions.add(new Question("What is the output of the following code?\n" +
                                   "String str1 = \"Java\";\n" +
                                   "String str2 = new String(\"Java\");\n" +
                                   "System.out.println(str1 == str2);", 
                                   List.of("true", "false", "Compilation Error"), 
                                   "false"));
        
        // Add more questions here
    }
}

class Question {
    private String question;
    private List<String> options;
    private String correctAnswer;

    public Question(String question, List<String> options, String correctAnswer) {
        this.question = question;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}
