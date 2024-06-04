import java.time.LocalDateTime;

public class Appointment {
    private String id;
    private Patient patient;
    private LocalDateTime dateTime;

    public Appointment(String id, Patient patient, LocalDateTime dateTime) {
        this.id = id;
        this.patient = patient;
        this.dateTime = dateTime;
    }

    public String getId() {
        return id;
    }

    public Patient getPatient() {
        return patient;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id='" + id + '\'' +
                ", patient=" + patient +
                ", dateTime=" + dateTime +
                '}';
    }
}
