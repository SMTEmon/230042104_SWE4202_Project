package studentmanagementsystem;

import studentmanagementsystem.ui.LoginFrame;
import javax.swing.SwingUtilities;

/**
 * Main class to run the Student Management System.
 */
public class Main {
    public static void main(String[] args) {
        // Ensure the UI is created on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}
