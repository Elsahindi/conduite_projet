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

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        messageLabel = new JLabel();
        messageLabel.setText("<html>" + request.getMessage() + "</html>");
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (showAcceptButton && !request.getStatus().equals(Status.ACCEPTED)) {
            acceptButton = new JButton("Accept");
            acceptButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            acceptButton.addActionListener(e -> {
                volunteer.acceptRequest(request);
                acceptButton.setVisible(false);
                messageLabel.setText("<html>Request accepted</html>");
            });


            this.add(acceptButton);
        } else {
            messageLabel.setText("<html>" + request.getMessage() + "<br/><b>Status: Accepted</b></html>");
        }

        this.add(messageLabel);

        this.add(Box.createVerticalStrut(10));

        this.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }
}
