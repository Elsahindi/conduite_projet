package ui.home;

import ui.request.RequestClientComponent;

import users.Client;

import request.Request;

import javax.swing.*;

import java.sql.SQLException;

import java.util.List;

public class ClientPanel extends JPanel {

    private JButton seeRequests;

    private JButton makeRequest;

    private JButton sendRequests;

    public ClientPanel(Client client) {

        seeRequests = new JButton("See Requests");
        makeRequest = new JButton("Make Request");
        this.add(seeRequests);
        this.add(makeRequest);

        seeRequests.addActionListener(e ->{
            List<Request> requests ;
            try {
                requests = client.getRequests();
                for (Request request : requests) {
                    RequestClientComponent r = new RequestClientComponent(request,client);
                    this.add(r);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

        });

        makeRequest.addActionListener(e ->{

            JLabel makeRequestLabel = new JLabel("Please write your request here : ");
            JTextArea makeRequestMessage = new JTextArea();
            sendRequests = new JButton("Send");

            this.add(makeRequestLabel);
            this.add(makeRequestMessage);
            this.add(sendRequests);

            System.out.println("test");

            sendRequests.addActionListener(ex ->{
                try {
                    Client.sendRequest(client.getId(),makeRequestMessage.getText(),client.getFacility());
                } catch (SQLException exc) {
                    throw new RuntimeException(exc);
                }

            });


        });
    }
}
