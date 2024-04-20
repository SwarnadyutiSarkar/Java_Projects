import java.util.*;

public class SchoolManagementSystem {
    private Map<String, Student> students;
    private Map<String, Teacher> teachers;
    private Map<String, Course> courses;
    private Map<String, List<Attendance>> attendanceRecords;

    public SchoolManagementSystem() {
        this.students = new HashMap<>();
        this.teachers = new HashMap<>();
        this.courses = new HashMap<>();
        this.attendanceRecords = new HashMap<>();
    }

    public void addStudent(String studentId, String studentName, int grade) {
        if (!students.containsKey(studentId)) {
            Student student = new Student(studentId, studentName, grade);
            students.put(studentId, student);
            System.out.println("Student added successfully.");
        } else {
            System.out.println("Student already exists.");
        }
    }

    public void addTeacher(String teacherId, String teacherName, String subject) {
        if (!teachers.containsKey(teacherId)) {
            Teacher teacher = new Teacher(teacherId, teacherName, subject);
            teachers.put(teacherId, teacher);
            System.out.println("Teacher added successfully.");
        } else {
            System.out.println("Teacher already exists.");
        }
    }

    public void addCourse(String courseId, String courseName, String teacherId) {
        if (!courses.containsKey(courseId) && teachers.containsKey(teacherId)) {
            Course course = new Course(courseId, courseName, teacherId);
            courses.put(courseId, course);
            System.out.println("Course added successfully.");
        } else {
            System.out.println("Course or teacher not found.");
        }
    }

    public void markAttendance(String courseId, String studentId, boolean isPresent) {
        if (courses.containsKey(courseId) && students.containsKey(studentId)) {
            Attendance attendance = new Attendance(studentId, isPresent);
            if (!attendanceRecords.containsKey(courseId)) {
                attendanceRecords.put(courseId, new ArrayList<>());
            }
            attendanceRecords.get(courseId).add(attendance);
            System.out.println("Attendance marked successfully.");
        } else {
            System.out.println("Course or student not found.");
        }
    }

    public void displayAttendance(String courseId) {
        if (attendanceRecords.containsKey(courseId)) {
            System.out.println("Attendance for Course " + courseId + ":");
            for (Attendance attendance : attendanceRecords.get(courseId)) {
                System.out.println(attendance);
            }
        } else {
            System.out.println("Course not found.");
        }
    }

    public static void main(String[] args) {
        SchoolManagementSystem system = new SchoolManagementSystem();

        // Add students
        system.addStudent("S001", "Alice", 10);
        system.addStudent("S002", "Bob", 11);

        // Add teachers
        system.addTeacher("T001", "Mr. Smith", "Math");
        system.addTeacher("T002", "Ms. Johnson", "Science");

        // Add courses
        system.addCourse("C001", "Mathematics", "T001");
        system.addCourse("C002", "Science", "T002");

        // Mark attendance
        system.markAttendance("C001", "S001", true);
        system.markAttendance("C001", "S002", false);

        // Display attendance
        system.displayAttendance("C001");
    }
}

class Student {
    private String studentId;
    private String studentName;
    private int grade;

    public Student(String studentId, String studentName, int grade) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", grade=" + grade +
                '}';
    }
}

class Teacher {
    private String teacherId;
    private String teacherName;
    private String subject;

    public Teacher(String teacherId, String teacherName, String subject) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId='" + teacherId + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}

class Course {
    private String courseId;
    private String courseName;
    private String teacherId;

    public Course(String courseId, String courseName, String teacherId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", teacherId='" + teacherId + '\'' +
                '}';
    }
}

class Attendance {
    private String studentId;
    private boolean isPresent;

    public Attendance(String studentId, boolean isPresent) {
        this.studentId = studentId;
        this.isPresent = isPresent;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "studentId='" + studentId + '\'' +
                ", isPresent=" + isPresent +
                '}';
    }
}
