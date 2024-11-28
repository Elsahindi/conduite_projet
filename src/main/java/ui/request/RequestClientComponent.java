package ui.request;

import request.Request;
import request.Status;
import users.Client;
import users.Volunteer;

import javax.swing.*;
import java.awt.*;

public class RequestClientComponent extends JPanel {

    private JLabel status;

    private JLabel message;

    public RequestClientComponent(Request request, Client client) {

        message = new JLabel(request.getMessage());

        status = new JLabel(request.getStatus().toString());

        this.add(status);
        this.add(message);

        if (request.getStatus().equals(Status.REJECTED)){
            JLabel motif = new JLabel(request.getMotif());
            this.add(motif);
        }
    }
}
