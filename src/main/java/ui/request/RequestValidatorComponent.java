package ui.request;

import request.Request;
import users.Validator;

import javax.swing.*;

public class RequestValidatorComponent extends JPanel {

    private JLabel messageLabel;

    private JButton cancelButton;

    private JButton okButton;

    public RequestValidatorComponent(Request request, Validator validator) {

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        messageLabel = new JLabel();
        messageLabel.setText(request.getMessage());

        cancelButton = new JButton();
        okButton = new JButton();
        cancelButton.setText("Reject");
        okButton.setText("Accept");

        okButton.addActionListener(e -> {
            validator.accepter(request);
        });

        cancelButton.addActionListener(e -> {
            validator.accepter(request);
        });

    }
}
