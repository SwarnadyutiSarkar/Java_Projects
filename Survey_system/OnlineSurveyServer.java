import java.io.*;
import java.net.*;
import java.util.*;

public class OnlineSurveyServer {
    private static final int PORT = 8080;
    private static final Map<String, Survey> surveys = new HashMap<>();
    private static final Map<String, List<SurveyResponse>> surveyResponses = new HashMap<>();

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
                    case "CREATE_SURVEY":
                        Survey newSurvey = (Survey) ois.readObject();
                        surveys.put(newSurvey.getId(), newSurvey);
                        surveyResponses.put(newSurvey.getId(), new ArrayList<>());
                        oos.writeObject("SURVEY_CREATED");
                        break;

                    case "GET_SURVEYS":
                        oos.writeObject(new ArrayList<>(surveys.values()));
                        break;

                    case "RESPOND_TO_SURVEY":
                        String surveyId = (String) ois.readObject();
                        Survey survey = surveys.get(surveyId);
                        if (survey != null) {
                            SurveyResponse response = (SurveyResponse) ois.readObject();
                            surveyResponses.get(surveyId).add(response);
                            oos.writeObject("SURVEY_RESPONSE_RECORDED");
                        } else {
                            oos.writeObject("SURVEY_NOT_FOUND");
                        }
                        break;

                    case "GET_SURVEY_RESPONSES":
                        String surveyIdForResponses = (String) ois.readObject();
                        List<SurveyResponse> responses = surveyResponses.get(surveyIdForResponses);
                        if (responses != null) {
                            oos.writeObject(responses);
                        } else {
                            oos.writeObject(new ArrayList<SurveyResponse>());
                        }
                        break;

                    case "EXIT":
                        return;
                }
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error handling client: " + e.getMessage());
        }
    }
}

class Survey implements Serializable {
    private String id;
    private String title;
    private List<String> questions;

    public Survey(String id, String title, List<String> questions) {
        this.id = id;
        this.title = title;
        this.questions = questions;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getQuestions() {
        return questions;
    }
}

class SurveyResponse implements Serializable {
    private String respondentName;
    private List<String> answers;

    public SurveyResponse(String respondentName, List<String> answers) {
        this.respondentName = respondentName;
        this.answers = answers;
    }

    public String getRespondentName() {
        return respondentName;
    }

    public List<String> getAnswers() {
        return answers;
    }
}
