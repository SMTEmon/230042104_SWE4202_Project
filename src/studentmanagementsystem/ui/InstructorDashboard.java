package studentmanagementsystem.ui;

import studentmanagementsystem.model.*;
import studentmanagementsystem.util.FileHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InstructorDashboard extends JFrame {

    private String instructorId;
    private JTable coursesTable;
    private JTable studentsTable;
    private DefaultTableModel coursesModel;
    private DefaultTableModel studentsModel;

    public InstructorDashboard(String instructorId) {
        this.instructorId = instructorId;
        setTitle("Instructor Dashboard - " + instructorId);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main layout
        setLayout(new BorderLayout(10, 10));

        // Top panel for courses
        JPanel coursesPanel = new JPanel(new BorderLayout());
        coursesPanel.setBorder(BorderFactory.createTitledBorder("My Courses"));
        coursesModel = new DefaultTableModel(new String[]{"Code", "Title", "Credits"}, 0);
        coursesTable = new JTable(coursesModel);
        coursesPanel.add(new JScrollPane(coursesTable), BorderLayout.CENTER);

        // Bottom panel for enrolled students
        JPanel studentsPanel = new JPanel(new BorderLayout());
        studentsPanel.setBorder(BorderFactory.createTitledBorder("Enrolled Students"));
        studentsModel = new DefaultTableModel(new String[]{"ID", "Name", "Grade"}, 0);
        studentsTable = new JTable(studentsModel);
        studentsPanel.add(new JScrollPane(studentsTable), BorderLayout.CENTER);

        // Button to assign grade
        JButton assignGradeButton = new JButton("Assign/Update Grade");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(assignGradeButton);
        studentsPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, coursesPanel, studentsPanel);
        splitPane.setResizeWeight(0.4);
        add(splitPane, BorderLayout.CENTER);

        // Load data
        loadCourses();

        // Add listener for course selection
        coursesTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = coursesTable.getSelectedRow();
                if (selectedRow != -1) {
                    String courseCode = coursesModel.getValueAt(selectedRow, 0).toString();
                    loadEnrolledStudents(courseCode);
                }
            }
        });

        // Add listener for assign grade button
        assignGradeButton.addActionListener(e -> assignGrade());
    }

    private void loadCourses() {
        coursesModel.setRowCount(0); // Clear existing data
        List<Course> allCourses = FileHandler.readCourses();
        allCourses.stream()
                .filter(course -> course.getInstructorId().equals(instructorId))
                .forEach(course -> coursesModel.addRow(new Object[]{course.getCourseCode(), course.getTitle(), course.getCredits()}));
    }

    private void loadEnrolledStudents(String courseCode) {
        studentsModel.setRowCount(0); // Clear existing data
        List<Map<String, String>> enrollments = FileHandler.readEnrollments();
        List<Student> students = FileHandler.readStudents();

        enrollments.stream()
                .filter(e -> e.get("courseCode").equals(courseCode))
                .forEach(enrollment -> {
                    Student student = students.stream()
                            .filter(s -> s.getId().equals(enrollment.get("studentID")))
                            .findFirst().orElse(null);
                    if (student != null) {
                        studentsModel.addRow(new Object[]{student.getId(), student.getName(), enrollment.get("grade")});
                    }
                });
    }

    private void assignGrade() {
        int selectedStudentRow = studentsTable.getSelectedRow();
        int selectedCourseRow = coursesTable.getSelectedRow();

        if (selectedStudentRow == -1 || selectedCourseRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a course and a student first.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String studentId = studentsModel.getValueAt(selectedStudentRow, 0).toString();
        String courseCode = coursesModel.getValueAt(selectedCourseRow, 0).toString();
        String currentGrade = studentsModel.getValueAt(selectedStudentRow, 2).toString();

        String newGrade = JOptionPane.showInputDialog(this, "Enter grade for " + studentId + " in " + courseCode + ":", currentGrade);

        if (newGrade != null && !newGrade.trim().isEmpty()) {
            FileHandler.updateGrade(studentId, courseCode, newGrade.trim().toUpperCase());
            JOptionPane.showMessageDialog(this, "Grade updated successfully!");
            // Refresh student list
            loadEnrolledStudents(courseCode);
        }
    }
}
