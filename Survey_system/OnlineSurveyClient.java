import java.io.*;
import java.net.*;
import java.util.*;

public class OnlineSurveyClient {
    private static final int PORT = 8080;
    private static final String SERVER_IP = "127.0.0.1";

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, PORT);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\nOnline Survey System");
                System.out.println("1. Create Survey");
                System.out.println("2. Respond to Survey");
                System.out.println("3. View Survey Responses");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        oos.writeObject("CREATE_SURVEY");
                        System.out.print("Enter survey ID: ");
                        String surveyId = scanner.nextLine();
                        System.out.print("Enter survey title: ");
                        String surveyTitle = scanner.nextLine();
                        System.out.print("Enter number of questions: ");
                        int numQuestions = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        List<String> questions = new ArrayList<>();
                        for (int i = 0; i < numQuestions; i++) {
                            System.out.print("Enter question " + (i + 1) + ": ");
                            questions.add(scanner.nextLine());
                        }

                        Survey newSurvey = new Survey(surveyId, surveyTitle, questions);
                        oos.writeObject(newSurvey);
                        String surveyCreationStatus = (String) ois.readObject();
                        System.out.println("Survey Creation Status: " + surveyCreationStatus);
                        break;

                    case 2:
                        oos.writeObject("GET_SURVEYS");
                        List<Survey> surveys = (List<Survey>) ois.readObject();
                        if (surveys.isEmpty()) {
                            System.out.println("No surveys available.");
                            break;
                        }

                        System.out.println("Available Surveys:");
                        for (Survey survey : surveys) {
                            System.out.println(survey.getId() + ": " + survey.getTitle());
                        }

                        System.out.print("Enter survey ID to respond: ");
                        String surveyIdToRespond = scanner.nextLine();
                        Survey selectedSurvey = surveys.stream().filter(s -> s.getId().equals(surveyIdToRespond)).findFirst().orElse(null);

                        if (selectedSurvey != null) {
                            List<String> answers = new ArrayList<>();
                            for (String question : selectedSurvey.getQuestions()) {
                                System.out.print(question + ": ");
                                answers.add(scanner.nextLine());
                            }

                            oos.writeObject("RESPOND_TO_SURVEY");
                            oos.writeObject(surveyIdToRespond);
                            SurveyResponse response = new SurveyResponse("User", answers);
                            oos.writeObject(response);
                            String responseStatus = (String) ois.readObject();
                            System.out.println("Response Status: " + responseStatus);
                        } else {
                            System.out.println("Invalid survey ID.");
                        }
                        break;

                    case 3:
                        oos.writeObject("GET_SURVEYS");
                        List<Survey> surveysForResponses = (List<Survey>) ois.readObject();
                        if (surveysForResponses.isEmpty()) {
                            System.out.println("No surveys available.");
                            break;
                        }

                        System.out.println("Available Surveys for Viewing Responses:");
                        for (Survey survey : surveysForResponses) {
                            System.out.println(survey.getId() + ": " + survey.getTitle());
                        }

                        System.out.print("Enter survey ID to view responses: ");
                        String surveyIdForResponses = scanner.nextLine();

                        oos.writeObject("GET_SURVEY_RESPONSES");
                        oos.writeObject(surveyIdForResponses);
                        List<SurveyResponse> responses = (List<SurveyResponse>) ois.readObject();

                        if (!responses.isEmpty()) {
                            System.out.println("Responses for Survey ID: " + surveyIdForResponses);
                            for (SurveyResponse response : responses) {
                                System.out.println("Respondent: " + response.getRespondentName());
                                for (int i = 0; i < response.getAnswers().size(); i++) {
                                    System.out.println(selectedSurvey.getQuestions().get(i) + ": " + response.getAnswers().get(i));
                                }
                                System.out.println("-------------------------");
                            }
                        } else {
                            System.out.println("No responses available for this survey.");
                        }
                        break;

                    case 4:
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
}
