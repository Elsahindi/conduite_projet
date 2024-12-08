package ui.request;

import request.Request;
import ui.home.ValidatorPanel;
import users.Validator;

import javax.swing.*;
import java.awt.*;

public class RequestValidatorComponent extends JPanel {

    public RequestValidatorComponent(ValidatorPanel panel, Request request, Validator validator) {

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel messageLabel = new JLabel(request.getMessage());
        messageLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(messageLabel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton okButton = new JButton("Accept");
        JButton cancelButton = new JButton("Reject");
        JButton rejectButton = new JButton("Reject");
        rejectButton.setVisible(false);
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(rejectButton);

        this.add(buttonPanel);

        JLabel titreMotif = new JLabel("Veuillez indiquer la raison du refus : ");
        titreMotif.setAlignmentX(Component.LEFT_ALIGNMENT);
        titreMotif.setVisible(false);
        this.add(titreMotif);

        JTextArea textArea = new JTextArea(5, 20);
        textArea.setEditable(true);
        textArea.setVisible(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        this.add(scrollPane);


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
        });

        rejectButton.addActionListener(e -> {
            validator.refuser(request, textArea.getText());
            panel.update();
        });

        this.add(Box.createVerticalStrut(10));
    }
}
