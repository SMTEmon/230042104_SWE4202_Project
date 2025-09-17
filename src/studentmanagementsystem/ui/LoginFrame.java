package studentmanagementsystem.ui;

import studentmanagementsystem.util.FileHandler;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {

    private JTextField userField;
    private JPasswordField passField;

    public LoginFrame() {
        setTitle("Student Management System - Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
        setLayout(new BorderLayout(10, 10));

        // Panel for input fields
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Username:"));
        userField = new JTextField();
        panel.add(userField);

        panel.add(new JLabel("Password:"));
        passField = new JPasswordField();
        panel.add(passField);

        // Panel for the button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton loginButton = new JButton("Login");
        buttonPanel.add(loginButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listener for login button
        loginButton.addActionListener(this::performLogin);
    }

    private void performLogin(ActionEvent e) {
        String username = userField.getText();
        String password = new String(passField.getPassword());

        String[] userDetails = FileHandler.authenticateUser(username, password);

        if (userDetails != null) {
            String role = userDetails[0];
            String userId = userDetails[1]; // The ID is now directly available

            // Open the appropriate dashboard
            switch (role) {
                case "Admin":
                    new AdminDashboard().setVisible(true);
                    break;
                case "Instructor":
                    new InstructorDashboard(userId).setVisible(true);
                    break;
                case "Student":
                    new StudentDashboard(userId).setVisible(true);
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Unknown role!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
            }
            this.dispose(); // Close login window
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}

