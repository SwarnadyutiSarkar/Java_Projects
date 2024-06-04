import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClinicManagementSystem {
    private List<Patient> patients = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public void scheduleAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public List<Patient> getAllPatients() {
        return patients;
    }

    public List<Appointment> getAllAppointments() {
        return appointments;
    }

    public static void main(String[] args) {
        ClinicManagementSystem cms = new ClinicManagementSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Clinic Management System");
            System.out.println("1. Add Patient");
            System.out.println("2. Schedule Appointment");
            System.out.println("3. List All Patients");
            System.out.println("4. List All Appointments");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Patient ID: ");
                    String patientId = scanner.nextLine();
                    System.out.print("Enter Patient Name: ");
                    String patientName = scanner.nextLine();
                    System.out.print("Enter Patient Age: ");
                    int patientAge = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter Patient Gender: ");
                    String patientGender = scanner.nextLine();
                    Patient patient = new Patient(patientId, patientName, patientAge, patientGender);
                    cms.addPatient(patient);
                    System.out.println("Patient added successfully.");
                    break;
                case 2:
                    System.out.print("Enter Appointment ID: ");
                    String appointmentId = scanner.nextLine();
                    System.out.print("Enter Patient ID for Appointment: ");
                    String appPatientId = scanner.nextLine();
                    Patient appPatient = cms.patients.stream()
                            .filter(p -> p.getId().equals(appPatientId))
                            .findFirst()
                            .orElse(null);
                    if (appPatient == null) {
                        System.out.println("Patient not found.");
                        break;
                    }
                    System.out.print("Enter Appointment Date and Time (YYYY-MM-DDTHH:MM): ");
                    String dateTimeStr = scanner.nextLine();
                    LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr);
                    Appointment appointment = new Appointment(appointmentId, appPatient, dateTime);
                    cms.scheduleAppointment(appointment);
                    System.out.println("Appointment scheduled successfully.");
                    break;
                case 3:
                    System.out.println("List of All Patients:");
                    for (Patient p : cms.getAllPatients()) {
                        System.out.println(p);
                    }
                    break;
                case 4:
                    System.out.println("List of All Appointments:");
                    for (Appointment a : cms.getAllAppointments()) {
                        System.out.println(a);
                    }
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
