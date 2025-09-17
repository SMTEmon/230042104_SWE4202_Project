package studentmanagementsystem.util;

import studentmanagementsystem.model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileHandler {

    private static final String DATA_DIR = "data/";
    private static final String LOGIN_FILE = DATA_DIR + "login.txt";
    private static final String STUDENTS_FILE = DATA_DIR + "students.txt";
    private static final String INSTRUCTORS_FILE = DATA_DIR + "instructors.txt";
    private static final String COURSES_FILE = DATA_DIR + "courses.txt";
    private static final String ENROLLMENTS_FILE = DATA_DIR + "enrollments.txt";

    // Static initializer to ensure data directory exists
    static {
        new File(DATA_DIR).mkdirs();
        try {
            new File(LOGIN_FILE).createNewFile();
            new File(STUDENTS_FILE).createNewFile();
            new File(INSTRUCTORS_FILE).createNewFile();
            new File(COURSES_FILE).createNewFile();
            new File(ENROLLMENTS_FILE).createNewFile();
        } catch (IOException e) {
            System.err.println("Error creating data files: " + e.getMessage());
        }
    }

    // Authenticate user
    public static String[] authenticateUser(String username, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader(LOGIN_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Format: username,password,role,id
                if (parts.length == 4 && parts[0].equals(username) && parts[1].equals(password)) {
                    // Return role and ID
                    return new String[]{parts[2], parts[3]};
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Authentication failed
    }

    // Generic read method
    private static List<String> readFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    // Generic append method
    private static void appendToFile(String filePath, String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(data);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Generic rewrite file method
    private static void writeFile(String filePath, List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) { // false to overwrite
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Student specific methods
    public static List<Student> readStudents() {
        List<Student> students = new ArrayList<>();
        List<String> lines = readFile(STUDENTS_FILE);
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 3) {
                students.add(new Student(parts[0], parts[1], parts[2]));
            }
        }
        return students;
    }

    public static void addStudent(Student student, String username, String password) {
        appendToFile(STUDENTS_FILE, student.toString());
        // Also save to login file with the Student ID for robust mapping
        appendToFile(LOGIN_FILE, username + "," + password + ",Student," + student.getId());
    }

    // Instructor specific methods
    public static List<Instructor> readInstructors() {
        List<Instructor> instructors = new ArrayList<>();
        List<String> lines = readFile(INSTRUCTORS_FILE);
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 3) {
                instructors.add(new Instructor(parts[0], parts[1], parts[2]));
            }
        }
        return instructors;
    }

    public static void addInstructor(Instructor instructor, String username, String password) {
        appendToFile(INSTRUCTORS_FILE, instructor.toString());
        // Also save to login file with the Instructor ID for robust mapping
        appendToFile(LOGIN_FILE, username + "," + password + ",Instructor," + instructor.getId());
    }

    // Course specific methods
    public static List<Course> readCourses() {
        List<Course> courses = new ArrayList<>();
        List<String> lines = readFile(COURSES_FILE);
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 4) {
                courses.add(new Course(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3]));
            }
        }
        return courses;
    }

    public static void addCourse(Course course) {
        appendToFile(COURSES_FILE, course.toString());
    }

    // Enrollment specific methods
    public static List<Map<String, String>> readEnrollments() {
        List<Map<String, String>> enrollments = new ArrayList<>();
        List<String> lines = readFile(ENROLLMENTS_FILE);
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 2) {
                Map<String, String> entry = new HashMap<>();
                entry.put("studentID", parts[0]);
                entry.put("courseCode", parts[1]);
                entry.put("grade", parts.length == 3 ? parts[2] : "Not Assigned");
                enrollments.add(entry);
            }
        }
        return enrollments;
    }

    public static void addEnrollment(String studentId, String courseCode) {
        appendToFile(ENROLLMENTS_FILE, studentId + "," + courseCode + ",");
    }

    public static void updateGrade(String studentId, String courseCode, String grade) {
        List<String> lines = readFile(ENROLLMENTS_FILE);
        List<String> updatedLines = new ArrayList<>();
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 2 && parts[0].equals(studentId) && parts[1].equals(courseCode)) {
                updatedLines.add(parts[0] + "," + parts[1] + "," + grade);
            } else {
                updatedLines.add(line);
            }
        }
        writeFile(ENROLLMENTS_FILE, updatedLines);
    }
}

