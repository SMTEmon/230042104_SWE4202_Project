package studentmanagementsystem.ui;

import studentmanagementsystem.model.*;
import studentmanagementsystem.util.FileHandler;
import javax.swing.*;
import java.awt.*;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        // Panels for each function
        tabbedPane.add("Add Student", createAddStudentPanel());
        tabbedPane.add("Add Instructor", createAddInstructorPanel());
        tabbedPane.add("Add Course", createAddCoursePanel());
        tabbedPane.add("Enroll Student", createEnrollStudentPanel());
        // You can add "Manage Records" panels similarly

        add(tabbedPane);
    }

    private JPanel createAddStudentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Student ID:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Department:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Password:"), gbc);

        // Text Fields
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // Allow text fields to expand horizontally
        JTextField idField = new JTextField(15);
        panel.add(idField, gbc);
        gbc.gridy++;
        JTextField nameField = new JTextField(15);
        panel.add(nameField, gbc);
        gbc.gridy++;
        JTextField deptField = new JTextField(15);
        panel.add(deptField, gbc);
        gbc.gridy++;
        JTextField userField = new JTextField(15);
        panel.add(userField, gbc);
        gbc.gridy++;
        JPasswordField passField = new JPasswordField(15);
        panel.add(passField, gbc);

        // Button
        gbc.gridx = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JButton addButton = new JButton("Add Student");
        panel.add(addButton, gbc);
        
        // Add vertical glue to push components to the top
        gbc.gridy++;
        gbc.weighty = 1.0;
        panel.add(new JLabel(), gbc);

        addButton.addActionListener(e -> {
            String id = idField.getText();
            String name = nameField.getText();
            String dept = deptField.getText();
            String user = userField.getText();
            String pass = new String(passField.getPassword());

            if(id.isEmpty() || name.isEmpty() || dept.isEmpty() || user.isEmpty() || pass.isEmpty()) {
                 JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                 return;
            }

            Student student = new Student(id, name, dept);
            FileHandler.addStudent(student, user, pass);
            JOptionPane.showMessageDialog(this, "Student added successfully!");
            // Clear fields
            idField.setText("");
            nameField.setText("");
            deptField.setText("");
            userField.setText("");
            passField.setText("");
        });

        return panel;
    }
    
    private JPanel createAddInstructorPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Instructor ID:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Department:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Username:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Password:"), gbc);

        // Text Fields
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        JTextField idField = new JTextField(15);
        panel.add(idField, gbc);
        gbc.gridy++;
        JTextField nameField = new JTextField(15);
        panel.add(nameField, gbc);
        gbc.gridy++;
        JTextField deptField = new JTextField(15);
        panel.add(deptField, gbc);
        gbc.gridy++;
        JTextField userField = new JTextField(15);
        panel.add(userField, gbc);
        gbc.gridy++;
        JPasswordField passField = new JPasswordField(15);
        panel.add(passField, gbc);

        // Button
        gbc.gridx = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JButton addButton = new JButton("Add Instructor");
        panel.add(addButton, gbc);

        // Add vertical glue to push components to the top
        gbc.gridy++;
        gbc.weighty = 1.0;
        panel.add(new JLabel(), gbc);

        addButton.addActionListener(e -> {
            String id = idField.getText();
            String name = nameField.getText();
            String dept = deptField.getText();
            String user = userField.getText();
            String pass = new String(passField.getPassword());

            if(id.isEmpty() || name.isEmpty() || dept.isEmpty() || user.isEmpty() || pass.isEmpty()) {
                 JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                 return;
            }

            Instructor instructor = new Instructor(id, name, dept);
            FileHandler.addInstructor(instructor, user, pass);
            JOptionPane.showMessageDialog(this, "Instructor added successfully!");
            // Clear fields
            idField.setText("");
            nameField.setText("");
            deptField.setText("");
            userField.setText("");
            passField.setText("");
        });

        return panel;
    }
    
    private JPanel createAddCoursePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Course Code:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Title:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Credits:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Instructor ID:"), gbc);

        // Text Fields
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        JTextField codeField = new JTextField(15);
        panel.add(codeField, gbc);
        gbc.gridy++;
        JTextField titleField = new JTextField(15);
        panel.add(titleField, gbc);
        gbc.gridy++;
        JTextField creditsField = new JTextField(15);
        panel.add(creditsField, gbc);
        gbc.gridy++;
        JTextField instIdField = new JTextField(15);
        panel.add(instIdField, gbc);

        // Button
        gbc.gridx = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JButton addButton = new JButton("Add Course");
        panel.add(addButton, gbc);
        
        // Add vertical glue to push components to the top
        gbc.gridy++;
        gbc.weighty = 1.0;
        panel.add(new JLabel(), gbc);

        addButton.addActionListener(e -> {
            String code = codeField.getText();
            String title = titleField.getText();
            String credits = creditsField.getText();
            String instId = instIdField.getText();

             if(code.isEmpty() || title.isEmpty() || credits.isEmpty() || instId.isEmpty()) {
                 JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                 return;
            }

            Course course = new Course(code, title, Integer.parseInt(credits), instId);
            FileHandler.addCourse(course);
            JOptionPane.showMessageDialog(this, "Course added successfully!");
            // Clear fields
            codeField.setText("");
            titleField.setText("");
            creditsField.setText("");
            instIdField.setText("");
        });
        
        return panel;
    }

    private JPanel createEnrollStudentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Student ID:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Course Code:"), gbc);

        // Text Fields
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        JTextField studentIdField = new JTextField(15);
        panel.add(studentIdField, gbc);
        gbc.gridy++;
        JTextField courseCodeField = new JTextField(15);
        panel.add(courseCodeField, gbc);

        // Button
        gbc.gridx = 1;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JButton enrollButton = new JButton("Enroll Student");
        panel.add(enrollButton, gbc);

        // Add vertical glue to push components to the top
        gbc.gridy++;
        gbc.weighty = 1.0;
        panel.add(new JLabel(), gbc);

        enrollButton.addActionListener(e -> {
            FileHandler.addEnrollment(studentIdField.getText(), courseCodeField.getText());
            JOptionPane.showMessageDialog(this, "Student enrolled successfully!");
        });

        return panel;
    }
}

