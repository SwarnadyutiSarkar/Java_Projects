import java.io.*;
import java.net.*;
import java.util.*;

public class OnlineExamClient {
    private static final int PORT = 8080;
    private static final String SERVER_IP = "127.0.0.1";

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, PORT);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\nOnline Examination System");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Create Exam");
                System.out.println("4. Start Exam");
                System.out.println("5. Submit Exam");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        oos.writeObject("REGISTER");
                        System.out.print("Enter username: ");
                        String regUsername = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String regPassword = scanner.nextLine();
                        User newUser = new User(regUsername, regPassword);
                        oos.writeObject(newUser);
                        String regStatus = (String) ois.readObject();
                        System.out.println("Registration Status: " + regStatus);
                        break;

                    case 2:
                        oos.writeObject("LOGIN");
                        System.out.print("Enter username: ");
                        String loginUsername = scanner.nextLine();
                        System.out.print("Enter password: ");
                        String loginPassword = scanner.nextLine();
                        String[] loginDetails = {loginUsername, loginPassword};
                        oos.writeObject(loginDetails);
                        String loginStatus = (String) ois.readObject();
                        System.out.println("Login Status: " + loginStatus);
                        break;

                    case 3:
                        oos.writeObject("CREATE_EXAM");
                        System.out.print("Enter exam ID: ");
                        String examId = scanner.nextLine();
                        System.out.print("Enter exam name: ");
                        String examName = scanner.nextLine();
                        List<Question> questions = new ArrayList<>();
                        while (true) {
                            System.out.print("Enter question text (or 'END' to finish): ");
                            String questionText = scanner.nextLine();
                            if ("END".equalsIgnoreCase(questionText)) {
                                break;
                            }
                            System.out.print("Enter options separated by commas: ");
                            List<String> options = Arrays.asList(scanner.nextLine().split(","));
                            System.out.print("Enter correct answer: ");
                            String correctAnswer = scanner.nextLine();
                            Question question = new Question(questionText, options, correctAnswer);
                            questions.add(question);
                        }
                        Exam newExam = new Exam(examId, examName, questions);
                        oos.writeObject(newExam);
                        String createExamStatus = (String) ois.readObject();
                        System.out.println("Exam Creation Status: " + createExamStatus);
                        break;

                    case 4:
                        oos.writeObject("START_EXAM");
                        System.out.print("Enter exam ID to start: ");
                        String startExamId = scanner.nextLine();
                        oos.writeObject(startExamId);
                        Object examObj = ois.readObject();
                        if (examObj instanceof Exam) {
                            Exam exam = (Exam) examObj;
                            takeExam(exam, oos, ois, scanner);
                        } else {
                            System.out.println("Error starting exam.");
                        }
                        break;

                    case 5:
                        oos.writeObject("SUBMIT_EXAM");
                        System.out.print("Enter exam ID to submit: ");
                        String submitExamId = scanner.nextLine();
                        System.out.println("Enter answers in format: questionId,answer");
                        Map<String, String> answers = new HashMap<>();
                        while (true) {
                            System.out.print("Enter answer (or 'END' to finish): ");
                            String answer = scanner.nextLine();
                            if ("END".equalsIgnoreCase(answer)) {
                                break;
                            }
                            String[] parts = answer.split(",");
                            if (parts.length == 2) {
                                answers.put(parts[0], parts[1]);
                            }
                        }
                        ExamSubmission submission = new ExamSubmission(submitExamId, answers);
                        oos.writeObject(submission);
                        String submitStatus = (String) ois.readObject();
                        System.out.println("Submit Status: " + submitStatus);
                        break;

                    case 6:
                        oos.writeObject("EXIT");
                        System.out.println("Exiting the program. Goodbye!");
                        return;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }

    private static void takeExam(Exam exam, ObjectOutputStream oos, ObjectInputStream ois, Scanner scanner) throws IOException, ClassNotFoundException {
        System.out.println("Starting Exam: " + exam.getName());
        for (Question question : exam.getQuestions()) {
            System.out.println(question.getText());
            int optionNum = 1;
            for (String option : question.getOptions()) {
                System.out.println(optionNum + ". " + option);
                optionNum++;
            }
            System.out.print("Enter your answer (1-" + (optionNum - 1) + "): ");
            int selectedOption = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            oos.writeObject(selectedOption - 1); // Send zero-based index of selected option
        }
        System.out.println("Exam completed. Waiting for results...");
        String result = (String) ois.readObject();
        System.out.println("Exam Result: " + result);
    }
}
