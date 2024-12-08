package ui.authentification;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeFrame extends JFrame {

    private JPanel panel1;

    private JButton createAnAccountButton;

    private JButton registerButton;

    public WelcomeFrame() {
        super("Welcome to the request service.");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);

        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());

        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome to the Request Service !", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        panel1.add(welcomeLabel, BorderLayout.NORTH);

        // Create a JPanel to hold the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));

        // Margin
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Create Account Button
        createAnAccountButton = new JButton("Create an Account");
        createAnAccountButton.setPreferredSize(new Dimension(150, 40));
        buttonPanel.add(createAnAccountButton);

        // Register Button
        registerButton = new JButton("Login");
        registerButton.setPreferredSize(new Dimension(150, 40));
        buttonPanel.add(registerButton);

        // Add the button panel to the main panel
        panel1.add(buttonPanel, BorderLayout.CENTER);

        // Action for "Create an Account" Button
        createAnAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCreateAccountPanel();
            }
        });

        // Action for "Register" Button
        registerButton.addActionListener(e -> showRegisterPanel());

        this.add(panel1);
        this.setVisible(true);
    }

    private void showCreateAccountPanel() {
        // Create the Create Account Panel
        CreateAccountPanel createAccountPanel = new CreateAccountPanel();

        // Create a custom JDialog
        JDialog createAccountDialog = new JDialog(this, "Create Account", true);
        createAccountDialog.setContentPane(createAccountPanel); // Set the content to the CreateAccountPanel
        createAccountDialog.setSize(400, 400); // Set the size of the dialog
        createAccountDialog.setLocationRelativeTo(this); // Center it relative to the main frame

        // Make the dialog visible
        createAccountDialog.setVisible(true);
    }



    private void showRegisterPanel() {

        // Create the Register Panel
        LoginPanel registerPanel = new LoginPanel();

        // Create a custom JDialog for Register
        JDialog registerDialog = new JDialog(this, "Login", true);
        registerDialog.setContentPane(registerPanel);
        registerDialog.setSize(400, 200);
        registerDialog.setLocationRelativeTo(this);
        // Make the dialog visible
        registerDialog.setVisible(true);
    }

}
