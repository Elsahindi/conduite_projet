package ui.home;

import users.Client;
import users.User;
import users.Validator;
import users.Volunteer;

import javax.swing.*;
import java.awt.*;

public class HomeFrame extends JFrame {

    private JLabel labelwelcome;

    public HomeFrame(User user) {
        super("Home");

        // Set up window properties
        this.setSize(600, 500);  // Slightly larger size
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null); // Center the window

        // Create a main panel with vertical BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE); // Set a clean white background

        // Create and style welcome label
        labelwelcome = new JLabel("Welcome " + user.getId());
        labelwelcome.setFont(new Font("Arial", Font.BOLD, 24)); // Larger, bold font
        labelwelcome.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align
        labelwelcome.setForeground(new Color(51, 51, 51)); // Dark gray color
        mainPanel.add(labelwelcome);
        mainPanel.add(Box.createVerticalStrut(20));  // Add vertical space after the welcome label

        // Conditionally add the appropriate panel based on the user type
        if (user instanceof Validator) {
            ValidatorPanel validatorPanel = new ValidatorPanel((Validator) user);
            stylePanel(validatorPanel);
            mainPanel.add(validatorPanel);
        } else if (user instanceof Volunteer) {
            VolunteerPanel volunteerPanel = new VolunteerPanel((Volunteer) user);
            stylePanel(volunteerPanel);
            mainPanel.add(volunteerPanel);
        } else if (user instanceof Client) {
            ClientPanel clientPanel = new ClientPanel((Client) user);
            stylePanel(clientPanel);
            mainPanel.add(clientPanel);
        }

        // Add the main panel to the frame
        this.add(mainPanel, BorderLayout.CENTER);
    }

    // Helper function to style each user panel
    private void stylePanel(JPanel panel) {
        panel.setBackground(new Color(240, 240, 240)); // Light gray background for the panel
        panel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220))); // Light border for separation
        panel.setPreferredSize(new Dimension(500, 300)); // Set a fixed size for consistency
        panel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align the panel within the main panel
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Keep vertical alignment
        panel.setMaximumSize(new Dimension(500, 600)); // Allow a max size but prevent it from becoming too large
    }
}
