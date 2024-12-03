package ui.home;

import request.Request;
import ui.request.RequestValidatorComponent;
import users.Validator;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class ValidatorPanel extends JPanel {

    private JButton seeRequests;
    private JButton backButton;
    private Validator validator;

    public ValidatorPanel(Validator validator) {
        this.validator = validator;
        update();
    }

    private void showRequests(Validator validator) {
        List<Request> requests;
        try {
            requests = validator.getRequests();

            for (Request request : requests) {
                RequestValidatorComponent r = new RequestValidatorComponent(this,request, validator);
                r.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
                this.add(r);
            }

            backButton = new JButton("Back");
            this.add(backButton);

            backButton.addActionListener(e -> {
                this.removeAll();
                seeRequests.setVisible(true);
                this.add(seeRequests);
                this.revalidate();
                this.repaint();
            });

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }


        this.revalidate();
        this.repaint();
    }

    public void update() {

        this.setVisible(false);
        this.removeAll();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        seeRequests = new JButton("See Requests");
        this.add(seeRequests);

        seeRequests.addActionListener(e -> {
            seeRequests.setVisible(false);
            showRequests(validator);
        });
        this.setVisible(true);
    }
}
