package ui.request;

import request.Request;
import request.Status;
import users.Volunteer;

import javax.swing.*;
import java.awt.*;

public class RequestVolunteerComponent extends JPanel {

    private JLabel messageLabel;
    private JButton acceptButton;

    public RequestVolunteerComponent(Request request, Volunteer volunteer, boolean showAcceptButton) {
        // Set layout for the panel
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Use vertical BoxLayout for a clean stack

        // Create a label to display the message
        messageLabel = new JLabel();
        messageLabel.setText("<html>" + request.getMessage() + "</html>"); // Ensure message can handle multiline
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align the message

        // Check the request's status to decide if the Accept button should be shown
        if (showAcceptButton && !request.getStatus().equals(Status.ACCEPTED)) {
            // Create the 'Accept' button only if the request hasn't been accepted
            acceptButton = new JButton("Accept");
            acceptButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align the button

            // Add action listener for the button
            acceptButton.addActionListener(e -> {
                volunteer.acceptRequest(request);
                acceptButton.setVisible(false);  // Hide the Accept button after it's clicked
                messageLabel.setText("<html>Request accepted</html>");  // Update the label to show acceptance
            });

            // Add the Accept button to the panel
            this.add(acceptButton);
        } else {
            // If the request is already accepted, show the message and a note
            messageLabel.setText("<html>" + request.getMessage() + "<br/><b>Status: Accepted</b></html>");
        }

        // Add the message label to the panel
        this.add(messageLabel);

        // Add some spacing between the message and the button (if the button exists)
        this.add(Box.createVerticalStrut(10));  // Add spacing between message and button

        // Set a preferred size to prevent the panel from resizing unexpectedly
        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150)); // Prevent the component from expanding too much
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding around the component
    }
}
