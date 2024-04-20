import java.io.*;
import java.net.*;
import java.util.*;

public class HealthcareManagementServer {
    private static final int PORT = 8080;
    private static final Map<String, Patient> patients = new HashMap<>();
    private static final Map<String, Doctor> doctors = new HashMap<>();
    private static final Map<String, Appointment> appointments = new HashMap<>();

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
                    case "ADD_PATIENT":
                        Patient newPatient = (Patient) ois.readObject();
                        patients.put(newPatient.getId(), newPatient);
                        oos.writeObject("PATIENT_ADDED");
                        break;

                    case "ADD_DOCTOR":
                        Doctor newDoctor = (Doctor) ois.readObject();
                        doctors.put(newDoctor.getId(), newDoctor);
                        oos.writeObject("DOCTOR_ADDED");
                        break;

                    case "SCHEDULE_APPOINTMENT":
                        Appointment newAppointment = (Appointment) ois.readObject();
                        if (patients.containsKey(newAppointment.getPatientId()) && doctors.containsKey(newAppointment.getDoctorId())) {
                            appointments.put(newAppointment.getId(), newAppointment);
                            oos.writeObject("APPOINTMENT_SCHEDULED");
                        } else {
                            oos.writeObject("ERROR");
                        }
                        break;

                    case "GET_APPOINTMENTS":
                        oos.writeObject(appointments.values());
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

class Patient implements Serializable {
    private String id;
    private String name;

    public Patient(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

class Doctor implements Serializable {
    private String id;
    private String name;
    private String specialization;

    public Doctor(String id, String name, String specialization) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }
}

class Appointment implements Serializable {
    private String id;
    private String patientId;
    private String doctorId;
    private Date date;

    public Appointment(String id, String patientId, String doctorId, Date date) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public Date getDate() {
        return date;
    }
}
