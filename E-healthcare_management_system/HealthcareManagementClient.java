import java.io.*;
import java.net.*;
import java.util.*;

public class HealthcareManagementClient {
    private static final int PORT = 8080;
    private static final String SERVER_IP = "127.0.0.1";

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, PORT);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\nHealthcare Management System");
                System.out.println("1. Add Patient");
                System.out.println("2. Add Doctor");
                System.out.println("3. Schedule Appointment");
                System.out.println("4. Get Appointments");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        oos.writeObject("ADD_PATIENT");
                        System.out.print("Enter patient ID: ");
                        String patientId = scanner.nextLine();
                        System.out.print("Enter patient name: ");
                        String patientName = scanner.nextLine();
                        Patient newPatient = new Patient(patientId, patientName);
                        oos.writeObject(newPatient);
                        String patientStatus = (String) ois.readObject();
                        System.out.println("Patient Status: " + patientStatus);
                        break;

                    case 2:
                        oos.writeObject("ADD_DOCTOR");
                        System.out.print("Enter doctor ID: ");
                        String doctorId = scanner.nextLine();
                        System.out.print("Enter doctor name: ");
                        String doctorName = scanner.nextLine();
                        System.out.print("Enter doctor specialization: ");
                        String doctorSpecialization = scanner.nextLine();
                        Doctor newDoctor = new Doctor(doctorId, doctorName, doctorSpecialization);
                        oos.writeObject(newDoctor);
                        String doctorStatus = (String) ois.readObject();
                        System.out.println("Doctor Status: " + doctorStatus);
                        break;

                    case 3:
                        oos.writeObject("SCHEDULE_APPOINTMENT");
                        System.out.print("Enter appointment ID: ");
                        String appointmentId = scanner.nextLine();
                        System.out.print("Enter patient ID: ");
                        String aptPatientId = scanner.nextLine();
                        System.out.print("Enter doctor ID: ");
                        String aptDoctorId = scanner.nextLine();
                        System.out.print("Enter appointment date (YYYY-MM-DD): ");
                        String aptDateStr = scanner.nextLine();
                        Date aptDate = new SimpleDateFormat("yyyy-MM-dd").parse(aptDateStr);
                        Appointment newAppointment = new Appointment(appointmentId, aptPatientId, aptDoctorId, aptDate);
                        oos.writeObject(newAppointment);
                        String appointmentStatus = (String) ois.readObject();
                        System.out.println("Appointment Status: " + appointmentStatus);
                        break;

                    case 4:
                        oos.writeObject("GET_APPOINTMENTS");
                        Object appointmentsObj = ois.readObject();
                        if (appointmentsObj instanceof Collection) {
                            Collection<Appointment> appointments = (Collection<Appointment>) appointmentsObj;
                            System.out.println("Appointments:");
                            for (Appointment apt : appointments) {
                                System.out.println("ID: " + apt.getId() + ", Patient ID: " + apt.getPatientId() + ", Doctor ID: " + apt.getDoctorId() + ", Date: " + apt.getDate());
                            }
                        } else {
                            System.out.println("No appointments available.");
                        }
                        break;

                    case 5:
                        oos.writeObject("EXIT");
                        System.out.println("Exiting the program. Goodbye!");
                        return;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }

        } catch (IOException | ClassNotFoundException | ParseException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}
