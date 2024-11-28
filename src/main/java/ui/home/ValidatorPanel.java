package ui.home;

import request.Request;
import ui.request.RequestValidatorComponent;
import ui.request.RequestVolunteerComponent;
import users.Validator;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ValidatorPanel extends JPanel {

    private JButton seeRequests;

    public ValidatorPanel(Validator validator) {

        seeRequests = new JButton("See Requests");
        seeRequests.addActionListener(e -> {
            List<Request> requests;
            try {
                requests = validator.getRequests();
                for (Request request : requests) {
                    RequestValidatorComponent r = new RequestValidatorComponent(request,validator);
                    this.add(r);
                }
            } catch (SQLException exception) {
                throw new RuntimeException(exception);
            }
        });
    }
}
