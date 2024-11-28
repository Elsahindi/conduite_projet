package ui.home;

import request.Request;
import ui.request.RequestVolunteerComponent;
import users.Volunteer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class VolunteerPanel extends JPanel {

    private JButton seeRequests;

    private JButton allRequests;

    public VolunteerPanel(Volunteer volunteer) {

        seeRequests = new JButton("My Requests");
        allRequests = new JButton("All Requests");
        this.add(seeRequests);
        this.add(allRequests);

        seeRequests.addActionListener(e -> {
            List<Request> requests;
            {
                try {
                    requests = volunteer.getRequests();
                    for (Request request : requests) {
                        RequestVolunteerComponent r = new RequestVolunteerComponent(request,volunteer);
                        this.add(r);
                    }
                } catch (SQLException exception) {
                    throw new RuntimeException(exception);
                }
            }
        });

        allRequests.addActionListener(e -> {
            List<Request> requests;
            {
                try{
                    requests = volunteer.seeAllRequests();
                    for (Request request : requests) {
                        RequestVolunteerComponent r = new RequestVolunteerComponent(request, volunteer);
                        this.add(r);
                    }
                } catch (SQLException exception) {
                    throw new RuntimeException(exception);
            }

            }
        });
    }



}
