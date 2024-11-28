package ui.authentification;

import javax.swing.*;
import java.awt.*;

// Panel for Register (only Username and Password)
public class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginPanel() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Username Field
        JLabel usernameLabel = new JLabel("Username :");
        usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(400, 30));


        add(usernameLabel);
        add(usernameField);

        // Password Field
        JLabel passwordLabel = new JLabel("Password :");
        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(400, 30));

        add(passwordLabel);
        add(passwordField);
    }
}

