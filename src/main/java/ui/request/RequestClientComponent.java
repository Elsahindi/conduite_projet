package ui.request;

import request.Request;
import request.Status;
import users.Client;

import javax.swing.*;

public class RequestClientComponent extends JPanel {

    public RequestClientComponent(Request request, Client client) {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        JLabel message = new JLabel(request.getMessage());
        JLabel status = new JLabel(request.getStatus().toString());


        this.add(status);
        this.add(message);


        if (request.getStatus().equals(Status.REJECTED)) {
            JLabel motif = new JLabel("Reason : " + request.getMotif());
            this.add(motif);
        }


        this.add(Box.createVerticalStrut(10));
    }
}
