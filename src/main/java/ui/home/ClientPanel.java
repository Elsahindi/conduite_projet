package ui.home;

import ui.request.RequestClientComponent;
import users.Client;
import request.Request;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ClientPanel extends JPanel {

    private JButton seeRequests;
    private JButton makeRequest;
    private JButton sendRequests;

    public ClientPanel(Client client) {
        // Set a BoxLayout to arrange components vertically with some spacing
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Initialize the buttons and add them to the panel
        seeRequests = new JButton("See Requests");
        makeRequest = new JButton("Make Request");

        // Add buttons with some space between them
        this.add(seeRequests);
        this.add(Box.createVerticalStrut(30)); // Add vertical spacing
        this.add(makeRequest);
        this.add(Box.createVerticalStrut(20)); // Add more space before actions

        // Action for 'See Requests' button
        seeRequests.addActionListener(e -> {
            this.removeAll(); // Clear previous components
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

            this.revalidate();
            this.repaint();
        });

        // Action for 'Make Request' button
        makeRequest.addActionListener(e -> {
            // Clear any existing components before adding new ones
            this.removeAll();

            // Create a label and text field for making a request
            JLabel makeRequestLabel = new JLabel("Please write your request here: ");
            makeRequestLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label

            JTextField makeRequestMessage = new JTextField(20); // Text field for request message
            makeRequestMessage.setMaximumSize(makeRequestMessage.getPreferredSize()); // Set max size for text field

            sendRequests = new JButton("Send");

            // Add borders for better spacing
            makeRequestLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            makeRequestMessage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            sendRequests.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Add components with appropriate vertical spacing
            this.add(makeRequestLabel);
            this.add(Box.createVerticalStrut(10)); // Space between label and text field
            this.add(makeRequestMessage);
            this.add(Box.createVerticalStrut(10)); // Space between text field and button
            this.add(sendRequests);

            // Action for 'Send' button
            sendRequests.addActionListener(ex -> {
                try {
                    Client.sendRequest(client.getId(), makeRequestMessage.getText(), client.getFacility());
                } catch (SQLException exc) {
                    throw new RuntimeException(exc);
                }
            });

            this.revalidate();
            this.repaint();
        });

        // Add margins around the whole panel
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Top, Left, Bottom, Right margins

    }
}
