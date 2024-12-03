package ui.authentification;

import ui.WindowManager;
import ui.home.ClientPanel;
import ui.home.HomeFrame;
import ui.home.ValidatorPanel;
import users.Client;
import users.User;
import users.Validator;
import users.Volunteer;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;


// Panel for Register (only Username and Password)
public class LoginPanel extends JPanel {

    public LoginPanel() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Username Field
        JLabel usernameLabel = new JLabel("Username :");
        JTextField usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(400, 30));


        add(usernameLabel);
        add(usernameField);

        // Password Field
        JLabel passwordLabel = new JLabel("Password :");
        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(400, 30));

        add(passwordLabel);
        add(passwordField);

        // Login Button
        JButton loginButton = new JButton("Login");
        add(loginButton);
        loginButton.addActionListener(e -> {
            int connected = User.login(usernameField.getText(),String.valueOf(passwordField.getPassword()));
            if (connected == 1) {
                User currentUser = null;
                try {
                    currentUser = User.getUser(usernameField.getText());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                if (currentUser instanceof Validator){
                    WindowManager.getInstance().setCurrentFrame(new HomeFrame((Validator)currentUser));
                } else if (currentUser instanceof Client) {
                    WindowManager.getInstance().setCurrentFrame(new HomeFrame((Client)currentUser));
                } else if (currentUser instanceof Volunteer) {
                    WindowManager.getInstance().setCurrentFrame(new HomeFrame((Volunteer)currentUser));
                }

            }
        });
       
    }
}

