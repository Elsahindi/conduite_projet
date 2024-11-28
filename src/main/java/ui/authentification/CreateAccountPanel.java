package ui.authentification;

import ui.home.HomeFrame;
import ui.WindowManager;
import users.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

// Panel for Create Account
public class CreateAccountPanel extends JPanel {

    private JTextField usernameField;

    private JPasswordField passwordField;

    private JRadioButton demanderButton, validatorButton, volunteerButton;

    private ButtonGroup roleGroup;

    private JPanel additionalPanel;

    private JRadioButton hospitalButton, schoolButton, retirementHouseButton;

    private ButtonGroup institutionGroup;

    private JLabel facilityLabel;

    private JButton registerButton;

    public CreateAccountPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(400,300));
        setBorder(BorderFactory.createTitledBorder("Create your account"));

        // Username Field
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        usernameField.setSize(200, 25);
        usernameField.setMaximumSize(new Dimension(300, 25));
        add(usernameLabel);
        add(usernameField);

        // Password Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        passwordField.setSize(200, 25);
        add(passwordLabel);
        add(passwordField);

        // Radio Buttons for Role Selection
        demanderButton = new JRadioButton("Demander");
        validatorButton = new JRadioButton("Validator");
        volunteerButton = new JRadioButton("Volunteer");

        // Group the radio buttons so only one can be selected at a time
        roleGroup = new ButtonGroup();
        roleGroup.add(demanderButton);
        roleGroup.add(validatorButton);
        roleGroup.add(volunteerButton);

        add(demanderButton);
        add(validatorButton);
        add(volunteerButton);

        // Panel for additional fields when Demander or Validator is selected
        additionalPanel = new JPanel();
        additionalPanel.setLayout(new BoxLayout(additionalPanel, BoxLayout.Y_AXIS));
        additionalPanel.setVisible(false); // Initially hidden

        // Label for facility selection
        facilityLabel = new JLabel("Please select the facility you are in:");
        additionalPanel.add(facilityLabel);

        // Radio Buttons for Institution Selection
        hospitalButton = new JRadioButton("Hospital");
        schoolButton = new JRadioButton("School");
        retirementHouseButton = new JRadioButton("Retirement House");

        // Group the institution radio buttons
        institutionGroup = new ButtonGroup();
        institutionGroup.add(hospitalButton);
        institutionGroup.add(schoolButton);
        institutionGroup.add(retirementHouseButton);

        additionalPanel.add(hospitalButton);
        additionalPanel.add(schoolButton);
        additionalPanel.add(retirementHouseButton);

        // Add additional panel to the main panel
        add(additionalPanel);

        // OK Button at the bottom of the panel
        registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(100, 40));
        add(registerButton);

        // Listener to toggle visibility of the additional panel
        ActionListener roleListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Hide institution options
                additionalPanel.setVisible(demanderButton.isSelected() || validatorButton.isSelected());// Show institution options
            }
        };

        demanderButton.addActionListener(roleListener);
        validatorButton.addActionListener(roleListener);
        volunteerButton.addActionListener(roleListener);

        // Add action listener to the OK button
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Facilities facility = null;
                User user = null;
                try {
                        if (hospitalButton.isSelected()) {
                            facility = Facilities.HOSPITAL;
                        }
                        if (schoolButton.isSelected()) {
                           facility = Facilities.SCHOOL;
                        }
                        if (retirementHouseButton.isSelected()) {
                            facility = Facilities.RETIREMENT;
                        }
                    System.out.println(facility);
                if (validatorButton.isSelected()) {
                    user = Validator.createValidator(usernameField.getText(),String.valueOf(passwordField.getPassword()),facility);
                }
                if (volunteerButton.isSelected()) {
                    user = Volunteer.createVolunteer(usernameField.getText(),String.valueOf(passwordField.getPassword()));
                }
                if (demanderButton.isSelected()) {
                    user = Client.createClient(usernameField.getText(),String.valueOf(passwordField.getPassword()),facility);
                }

                }catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                WindowManager.getInstance().setCurrentFrame(new HomeFrame(user));

            }
        });
    }
}


