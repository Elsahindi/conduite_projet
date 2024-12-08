package ui.review;

import users.User;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

import static review.Review.createReview;
import static users.User.sendReview;

public class ReviewComponent extends JPanel {

    private JTextField reviewTextArea;
    private JButton submitReviewButton;

    public ReviewComponent(User user) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create the review label
        JLabel reviewLabel = new JLabel("Your opinion matters, please give us your feedback :");
        reviewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(reviewLabel);

        // Create the text area for review input
        reviewTextArea = new JTextField();
        reviewTextArea.setPreferredSize(new Dimension(400, 50));
        reviewTextArea.setMaximumSize(new Dimension(400, 50));
        JScrollPane reviewScrollPane = new JScrollPane(reviewTextArea);
        reviewScrollPane.setPreferredSize(new Dimension(300, 50));
        this.add(reviewScrollPane);

        // Create the submit button
        submitReviewButton = new JButton("Submit Review");
        submitReviewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitReviewButton.addActionListener(e -> {
            String reviewText = reviewTextArea.getText();
            reviewTextArea.setSize(new Dimension(300, 50));
            reviewTextArea.setMaximumSize(new Dimension(300, 50));
            if (!reviewText.trim().isEmpty()) {
                try {
                    sendReview(user, "My review on the Mutual Aid App",reviewTextArea.getText());
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please provide a review before submitting.");
            }
            submitReviewButton.setVisible(false);
            reviewTextArea.setVisible(false);
            JOptionPane.showMessageDialog(this, "Thank you for your feedback!");

        });
        this.add(submitReviewButton);
    }

}
