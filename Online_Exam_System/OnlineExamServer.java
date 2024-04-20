import java.io.*;
import java.net.*;
import java.util.*;

public class OnlineExamServer {
    private static final int PORT = 8080;
    private static final Map<String, User> users = new HashMap<>();
    private static final Map<String, Exam> exams = new HashMap<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream())) {

            while (true) {
                String action = (String) ois.readObject();

                switch (action) {
                    case "REGISTER":
                        User newUser = (User) ois.readObject();
                        users.put(newUser.getUsername(), newUser);
                        oos.writeObject("REGISTERED");
                        break;

                    case "LOGIN":
                        String[] loginDetails = (String[]) ois.readObject();
                        String username = loginDetails[0];
                        String password = loginDetails[1];
                        if (users.containsKey(username) && users.get(username).getPassword().equals(password)) {
                            oos.writeObject("LOGGED_IN");
                        } else {
                            oos.writeObject("ERROR");
                        }
                        break;

                    case "CREATE_EXAM":
                        Exam newExam = (Exam) ois.readObject();
                        exams.put(newExam.getId(), newExam);
                        oos.writeObject("EXAM_CREATED");
                        break;

                    case "START_EXAM":
                        String examId = (String) ois.readObject();
                        if (exams.containsKey(examId)) {
                            Exam exam = exams.get(examId);
                            oos.writeObject(exam);
                        } else {
                            oos.writeObject("ERROR");
                        }
                        break;

                    case "SUBMIT_EXAM":
                        ExamSubmission submission = (ExamSubmission) ois.readObject();
                        double score = calculateScore(submission);
                        oos.writeObject("SCORE: " + score);
                        break;

                    case "EXIT":
                        return;
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error handling client: " + e.getMessage());
        }
    }

    private static double calculateScore(ExamSubmission submission) {
        // Dummy score calculation logic
        return Math.random() * 100;
    }
}

class User implements Serializable {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

class Exam implements Serializable {
    private String id;
    private String name;
    private List<Question> questions;

    public Exam(String id, String name, List<Question> questions) {
        this.id = id;
        this.name = name;
        this.questions = questions;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}

class Question implements Serializable {
    private String text;
    private List<String> options;
    private String correctAnswer;

    public Question(String text, List<String> options, String correctAnswer) {
        this.text = text;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

    public String getText() {
        return text;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
}

class ExamSubmission implements Serializable {
    private String examId;
    private Map<String, String> answers;

    public ExamSubmission(String examId, Map<String, String> answers) {
        this.examId = examId;
        this.answers = answers;
    }

    public String getExamId() {
        return examId;
    }

    public Map<String, String> getAnswers() {
        return answers;
    }
}
