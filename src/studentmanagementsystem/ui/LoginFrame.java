package studentmanagementsystem.ui;

import studentmanagementsystem.util.FileHandler;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {
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

    private JTextField userField;
    private JPasswordField passField;

    // ...removed dark mode feature...

    public LoginFrame() {
        setTitle("Login");
        setSize(300, 160);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(5, 5));

    // Create panel for username and password fields
        JPanel panel = new JPanel(new GridLayout(2, 2, 2, 2));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(userLabel);
        userField = new JTextField();
        userField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(userField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(passLabel);
        passField = new JPasswordField();
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panel.add(passField);

        // Create panel for login button only
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        loginButton.setBorder(new RoundedBorder(16));
        loginButton.setFocusPainted(false);
        loginButton.setContentAreaFilled(true);
        loginButton.setBackground(new Color(220, 235, 255));
        buttonPanel.add(loginButton);

        // Add panels to frame
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Handle login button click
        loginButton.addActionListener(this::performLogin);
    }

    // ...removed dark mode theme logic...

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

