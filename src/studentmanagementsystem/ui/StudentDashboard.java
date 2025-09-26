package studentmanagementsystem.ui;

import studentmanagementsystem.model.*;
import studentmanagementsystem.util.FileHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentDashboard extends JFrame {
    // Custom rounded border for modern buttons
    private static class RoundedBorder extends javax.swing.border.AbstractBorder {
        private final int radius;
        public RoundedBorder(int radius) { this.radius = radius; }
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(Color.GRAY);
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
        @Override
        public Insets getBorderInsets(Component c) { return new Insets(4, 8, 4, 8); }
        @Override
        public Insets getBorderInsets(Component c, Insets insets) { return getBorderInsets(c); }
    }

    private String studentId;

    public StudentDashboard(String studentId) {
        this.studentId = studentId;
        setTitle("Student - " + studentId);
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        tabbedPane.add("My Profile", createProfilePanel());
        tabbedPane.add("Enrolled Courses", createCoursesPanel());
        tabbedPane.add("Transcript", createTranscriptPanel());

        add(tabbedPane);
    }

    private JPanel createProfilePanel() {
    JPanel panel = new JPanel(new GridLayout(3, 2, 2, 2));
    panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        Student student = FileHandler.readStudents().stream()
                .filter(s -> s.getId().equals(studentId))
                .findFirst().orElse(null);

        if (student != null) {
            panel.add(new JLabel("Student ID:"));
            panel.add(new JLabel(student.getId()));
            panel.add(new JLabel("Name:"));
            panel.add(new JLabel(student.getName()));
            panel.add(new JLabel("Department:"));
            panel.add(new JLabel(student.getDepartment()));
        } else {
            panel.add(new JLabel("Student data not found."));
        }
        return panel;
    }

    private JPanel createCoursesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columns = {"Course Code", "Title", "Credits", "Grade"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
    JTable table = new JTable(model);
    // Example: If you add any action buttons to this panel, make them rounded like below
    // JButton someButton = new JButton("Action");
    // someButton.setBorder(new RoundedBorder(16));
    // someButton.setFocusPainted(false);
    // someButton.setContentAreaFilled(true);
    // someButton.setBackground(new Color(220, 235, 255));

        List<Map<String, String>> enrollments = FileHandler.readEnrollments();
        List<Course> courses = FileHandler.readCourses();

        enrollments.stream()
                .filter(e -> e.get("studentID").equals(studentId))
                .forEach(enrollment -> {
                    Course course = courses.stream()
                            .filter(c -> c.getCourseCode().equals(enrollment.get("courseCode")))
                            .findFirst().orElse(null);
                    if (course != null) {
                        model.addRow(new Object[]{
                                course.getCourseCode(),
                                course.getTitle(),
                                course.getCredits(),
                                enrollment.get("grade")
                        });
                    }
                });

        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createTranscriptPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextArea transcriptArea = new JTextArea();
        transcriptArea.setEditable(false);
        transcriptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        StringBuilder sb = new StringBuilder();
        sb.append("---------------- TRANSCRIPT ----------------\n\n");

        List<Map<String, String>> enrollments = FileHandler.readEnrollments().stream()
                .filter(e -> e.get("studentID").equals(studentId)).collect(Collectors.toList());
        List<Course> courses = FileHandler.readCourses();

        double totalCredits = 0;
        double totalGradePoints = 0;

        sb.append(String.format("%-15s %-30s %-10s %-10s\n", "Course Code", "Title", "Credits", "Grade"));
        sb.append("-----------------------------------------------------------------\n");

        for (Map<String, String> enrollment : enrollments) {
            Course course = courses.stream()
                    .filter(c -> c.getCourseCode().equals(enrollment.get("courseCode")))
                    .findFirst().orElse(null);

            String gradeStr = enrollment.get("grade");

            if (course != null && gradeStr != null && !gradeStr.equals("Not Assigned")) {
                double gradePoint = convertGradeToPoint(gradeStr);
                int credits = course.getCredits();

                totalCredits += credits;
                totalGradePoints += gradePoint * credits;

                sb.append(String.format("%-15s %-30s %-10d %-10s\n",
                        course.getCourseCode(), course.getTitle(), credits, gradeStr));
            }
        }

        double cgpa = (totalCredits > 0) ? totalGradePoints / totalCredits : 0.0;

        sb.append("\n-----------------------------------------------------------------\n");
        sb.append(String.format("Cumulative GPA (CGPA): %.2f\n", cgpa));

        transcriptArea.setText(sb.toString());
        panel.add(new JScrollPane(transcriptArea), BorderLayout.CENTER);

        return panel;
    }

    private double convertGradeToPoint(String grade) {
        switch (grade.toUpperCase()) {
            case "A": return 4.0;
            case "A-": return 3.7;
            case "B+": return 3.3;
            case "B": return 3.0;
            case "B-": return 2.7;
            case "C+": return 2.3;
            case "C": return 2.0;
            case "D": return 1.0;
            default: return 0.0; // F
        }
    }
}
