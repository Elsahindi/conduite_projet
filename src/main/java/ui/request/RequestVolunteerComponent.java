package ui.request;

import request.Request;
import users.Volunteer;

import javax.swing.*;
import java.awt.*;

public class RequestVolunteerComponent extends JPanel {

    private JLabel message;

    private JButton acceptButton;

    public RequestVolunteerComponent(Request request, Volunteer volunteer) {

        this.setLayout(new BorderLayout());

        message = new JLabel();
        message.setText(request.getMessage());

        acceptButton = new JButton("Accept");
        acceptButton.addActionListener(e -> {
            volunteer.acceptRequest(request);
        });

    }
}
