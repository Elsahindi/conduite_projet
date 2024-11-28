package ui.authentification;

import javax.swing.*;

// Panel for Register (only Username and Password)
public class RegisterPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public RegisterPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Username Field
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        add(usernameLabel);
        add(usernameField);

        // Password Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        add(passwordLabel);
        add(passwordField);
    }
}

