package ui.home;

import request.Request;
import request.Status;
import ui.request.RequestVolunteerComponent;
import ui.review.ReviewComponent;
import users.Volunteer;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class VolunteerPanel extends JPanel {

    private JButton seeRequests;
    private JButton allRequests;
    private JPanel requestsPanel; // A panel to hold the requests dynamically

    public VolunteerPanel(Volunteer volunteer) {

        // Initialize the panel
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Initialize buttons
        seeRequests = new JButton("My Requests");
        allRequests = new JButton("All Requests");

        // Add the buttons to the panel
        this.add(seeRequests);
        this.add(allRequests);

        // Panel to hold request components
        requestsPanel = new JPanel();
        requestsPanel.setLayout(new BoxLayout(requestsPanel, BoxLayout.Y_AXIS)); // Stack the requests vertically
        this.add(requestsPanel);

        // Create a JScrollPane to make the requestsPanel scrollable
        JScrollPane scrollPane = new JScrollPane(requestsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Always show vertical scrollbar
        this.add(scrollPane); // Add the scrollPane to the panel

        ReviewComponent reviewComponent = new ReviewComponent(volunteer);
        this.add(reviewComponent);

        // Action when "My Requests" is clicked
        seeRequests.addActionListener(e -> {
            // Clear the panel first
            requestsPanel.removeAll();
            try {
                // Get requests and add them without the accept button if accepted
                List<Request> requests = volunteer.getRequests();
                for (Request request : requests) {
                    boolean showAcceptButton = !request.getStatus().equals(Status.ACCEPTED); // Don't show Accept if already accepted
                    RequestVolunteerComponent r = new RequestVolunteerComponent(request, volunteer, showAcceptButton);
                    requestsPanel.add(r);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            this.revalidate();
            this.repaint();
        });

        // Action when "See All Requests" is clicked
        allRequests.addActionListener(e -> {
            // Clear the panel first
            requestsPanel.removeAll();
            try {
                // Get all requests and add them with the accept button for requests not yet accepted
                List<Request> requests = volunteer.seeAllRequests();
                for (Request request : requests) {
                    RequestVolunteerComponent r = new RequestVolunteerComponent(request, volunteer, true); // Show Accept button
                    requestsPanel.add(r);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            this.revalidate();
            this.repaint();
        });
    }
}
