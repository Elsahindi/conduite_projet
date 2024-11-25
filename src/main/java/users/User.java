package users;

import database.DatabaseCreation;
import request.Request;
import review.Review;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class User {

    private String id;
    private String pswd;

    public User(String id, String pswd) {
        this.id = id;
        this.pswd = pswd;
    }

    public String getId() {
        return id;
    }

    public String getPswd() {
        return pswd;
    }

    // Method to create a new user in the database
    public static void createUser(String id, String pswd) throws SQLException {
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("INSERT INTO user (id, pswd) VALUES (?,?)");
        statement.setString(1, id);
        statement.setString(2, pswd);
        statement.execute();
    }

    // Abstract method for user login
    public abstract int login(String id, String pswd);

    // Abstract method to get the list of requests associated with the user
    public abstract List<Request> getRequests() throws SQLException;

    // Method for a user to send a review
    public static Review sendReview(User Author, String title, String content) throws SQLException {
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("INSERT INTO review (idAuthor, title, content) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1,Author.getId());
        statement.setString(2,title );
        statement.setString(3,content);
        statement.executeUpdate();

        ResultSet generatedKeys = statement.getGeneratedKeys();
        generatedKeys.next();
        return Review.createReview(generatedKeys.getInt(1),Author.getId(), title,content);
    }

    // Method to get all reviews from the database
    public static List<Review> getReviews() throws SQLException {
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("SELECT * FROM review");
        ResultSet resultSet = statement.executeQuery();

        List<Review> reviews = new ArrayList<>();
        while (resultSet.next()) {
            reviews.add(new Review(resultSet.getInt("idReview"),
                    resultSet.getString("idAuthor"),
                    resultSet.getString("title"),
                    resultSet.getString("content")));
        }
        return reviews;
    }

    // Method for a user to get only their own reviews (filtered by user ID)
    public List<Review> getMyReviews() throws SQLException {
        List<Review> reviews = getReviews();
        // Use an iterator to safely remove elements while iterating
        Iterator<Review> iterator = reviews.iterator();
        while (iterator.hasNext()) {
            Review review = iterator.next();
            if (!review.getidAuthor().equals(getId())) {
                iterator.remove();
            }
        }
        return reviews;
    }
}