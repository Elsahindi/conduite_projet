package ui.request;

import request.Request;
import ui.home.ValidatorPanel;
import users.Validator;

import javax.swing.*;

public class RequestValidatorComponent extends JPanel {

    public RequestValidatorComponent(ValidatorPanel panel, Request request, Validator validator) {

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel messageLabel = new JLabel();
        messageLabel.setText(request.getMessage());
        this.add(messageLabel);

        JButton cancelButton = new JButton();
        JButton okButton = new JButton();
        JButton rejectButton = new JButton();
        cancelButton.setText("Reject");
        okButton.setText("Accept");
        rejectButton.setText("Reject");
        this.add(cancelButton);
        this.add(okButton);
        rejectButton.setVisible(false);
        this.add(rejectButton);


        JLabel titreMotif = new JLabel("Veuillez indiquer la raison du refus : ");
        titreMotif.setVisible(false);
        this.add(titreMotif);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(true);
        this.add(textArea);

        okButton.addActionListener(e -> {
            validator.accepter(request);
            panel.update();
        });

        cancelButton.addActionListener(e -> {
            cancelButton.setVisible(false);
            okButton.setVisible(false);
            titreMotif.setVisible(true);
            textArea.setVisible(true);
            rejectButton.setVisible(true);
            validator.refuser(request,textArea.getText());
        });

        rejectButton.addActionListener(e -> {
            validator.refuser(request,textArea.getText());
            System.out.println(request.getStatus());
            panel.update();
        });

    }
}
