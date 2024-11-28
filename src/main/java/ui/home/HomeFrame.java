package ui.home;

import users.Client;
import users.User;
import users.Validator;
import users.Volunteer;

import javax.swing.*;
import java.awt.*;

import static javax.swing.BoxLayout.Y_AXIS;

public class HomeFrame extends JFrame {

    private JLabel labelwelcome;


    public HomeFrame(User user) {

        super("Home");
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,Y_AXIS));

        labelwelcome = new JLabel("Welcome " + user.getId());
        panel.add(labelwelcome);

        if (user instanceof Validator){
            ValidatorPanel validatorPanel = new ValidatorPanel((Validator) user);
            panel.add(validatorPanel);
        }

        if (user instanceof Volunteer){
            VolunteerPanel volunteerPanel = new VolunteerPanel((Volunteer) user);
            panel.add(volunteerPanel);
        }

        if (user instanceof Client){
            ClientPanel clientPanel = new ClientPanel((Client) user);
            panel.add(clientPanel);
        }

        this.add(panel, BorderLayout.CENTER);


    }
}
