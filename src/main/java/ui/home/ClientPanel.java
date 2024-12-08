package ui.home;

import ui.request.RequestClientComponent;
import users.Client;
import request.Request;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ClientPanel extends JPanel {

    private JButton sendRequests;
    private JButton backButton;
    private JTextField makeRequestMessage;

    public ClientPanel(Client client) {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


        JButton seeRequests = new JButton("See Requests");
        JButton makeRequest = new JButton("Make Request");


        this.add(seeRequests);
        this.add(Box.createVerticalStrut(30));
        this.add(makeRequest);
        this.add(Box.createVerticalStrut(20));

        JPanel requestsPanel = new JPanel();
        requestsPanel.setLayout(new BoxLayout(requestsPanel, BoxLayout.Y_AXIS)); // Stack the requests vertically
        this.add(requestsPanel);
        JScrollPane scrollPane = new JScrollPane(requestsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Always show vertical scrollbar
        this.add(scrollPane); // Add the scrollPane to the panel

        seeRequests.addActionListener(e -> {
            this.removeAll();
            List<Request> requests;
            try {
                requests = client.getRequests();
                for (Request request : requests) {
                    RequestClientComponent r = new RequestClientComponent(request, client);
                    this.add(r);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            addBackButton(client);

            this.revalidate();
            this.repaint();
        });


        makeRequest.addActionListener(e -> {

            this.removeAll();

            JLabel makeRequestLabel = new JLabel("Please write your request here: ");
            makeRequestLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            makeRequestMessage = new JTextField(20);
            makeRequestMessage.setMaximumSize(makeRequestMessage.getPreferredSize());

            sendRequests = new JButton("Send");


            makeRequestLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            makeRequestMessage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            sendRequests.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


            this.add(makeRequestLabel);
            this.add(Box.createVerticalStrut(10));
            this.add(makeRequestMessage);
            this.add(Box.createVerticalStrut(10));
            this.add(sendRequests);


            addBackButton(client);


            sendRequests.addActionListener(ex -> {
                try {

                    Client.sendRequest(client.getId(), makeRequestMessage.getText(), client.getFacility());


                    makeRequestMessage.setVisible(false);
                    sendRequests.setVisible(false);
                    JLabel messageSentLabel = new JLabel("Request Sent");
                    messageSentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    messageSentLabel.setForeground(Color.GREEN);
                    this.add(messageSentLabel);
                    this.revalidate();
                    this.repaint();

                    addBackButton(client);

                } catch (SQLException exc) {
                    throw new RuntimeException(exc);
                }
            });

            this.revalidate();
            this.repaint();
        });


        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private void addBackButton(Client client) {

        if (backButton != null) {
            this.remove(backButton);
        }

        backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            this.removeAll();
            initializeMainButtons(client);
            this.revalidate();
            this.repaint();
        });
        this.add(backButton);
    }

    private void initializeMainButtons(Client client) {
        JButton seeRequests = new JButton("See Requests");
        JButton makeRequest = new JButton("Make Request");


        this.add(seeRequests);
        this.add(Box.createVerticalStrut(30));
        this.add(makeRequest);
        this.add(Box.createVerticalStrut(20));


        seeRequests.addActionListener(e -> {
            this.removeAll();
            List<Request> requests;
            try {
                requests = client.getRequests();
                for (Request request : requests) {
                    RequestClientComponent r = new RequestClientComponent(request, client);
                    this.add(r);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }

            addBackButton(client);

            this.revalidate();
            this.repaint();
        });

        makeRequest.addActionListener(e -> {
            this.removeAll();
            JLabel makeRequestLabel = new JLabel("Please write your request here: ");
            makeRequestLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            makeRequestMessage = new JTextField(20);
            makeRequestMessage.setMaximumSize(makeRequestMessage.getPreferredSize());

            sendRequests = new JButton("Send");


            makeRequestLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            makeRequestMessage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            sendRequests.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


            this.add(makeRequestLabel);
            this.add(Box.createVerticalStrut(10));
            this.add(makeRequestMessage);
            this.add(Box.createVerticalStrut(10));
            this.add(sendRequests);


            addBackButton(client);

            sendRequests.addActionListener(ex -> {
                try {
                    Client.sendRequest(client.getId(), makeRequestMessage.getText(), client.getFacility());

                    makeRequestMessage.setVisible(false);
                    sendRequests.setVisible(false);
                    JLabel messageSentLabel = new JLabel("Message Sent");
                    messageSentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    messageSentLabel.setForeground(Color.GREEN);
                    this.add(messageSentLabel);
                    this.revalidate();
                    this.repaint();

                    addBackButton(client);

                } catch (SQLException exc) {
                    throw new RuntimeException(exc);
                }
            });

            this.revalidate();
            this.repaint();
        });

        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }
}
