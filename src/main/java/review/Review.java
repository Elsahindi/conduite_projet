package review;

import database.DatabaseCreation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Review {

    private int idReview;
    private String idAuthor;
    private String title;
    private String content;

    public Review(int idReview, String idAuthor, String title, String content) {
        this.idReview = idReview;
        this.idAuthor = idAuthor;
        this.title = title;
        this.content = content;
    }

    public int getIdReview() { return idReview; }
    public void setIdReview(int idReview) { this.idReview = idReview; }

    public String getidAuthor(){
        return idAuthor;
    }
    public void setAuthor(String idAuthor) {
        this.idAuthor = idAuthor;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    // Static method to create a review.Review object
    public static Review createReview(int idReview, String idAuthor, String title, String content) {
        return new Review(idReview, idAuthor, title, content);
    }

    public String getIdAuthor() {
        return idAuthor;
    }

    // Method to save or update the review.Review in the database
    public void save(){
        try {
            boolean exists;
            try (PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                    .prepareStatement("SELECT 1 FROM review WHERE idReview = ?")) {
                statement.setInt(1, idReview);
                ResultSet resultSet = statement.executeQuery();
                exists = resultSet.next();
            }
            if (exists) {
                try (PreparedStatement updateStatement = DatabaseCreation.getInstance().getConnection()
                            .prepareStatement("UPDATE review SET idAuthor = ?, title = ?, content WHERE idReview = ?")){
                    updateStatement.setString(1,idAuthor);
                    updateStatement.setString(2,title);
                    updateStatement.setString(3,content);
                    updateStatement.setInt(4,idReview);
                    updateStatement.executeUpdate();}
            } else {
                try (PreparedStatement updateStatement = DatabaseCreation.getInstance().getConnection()
                        .prepareStatement("INSERT INTO review (idReview, idAuthor, title, content)  VALUES (?, ?, ?, ?)")){
                    updateStatement.setInt(1,idReview);
                    updateStatement.setString(2,idAuthor);
                    updateStatement.setString(3,title);
                    updateStatement.setString(4,content);
                    updateStatement.executeUpdate();}
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String toString() {
        return idAuthor + "\t" + title + "\t" + content;
    }
}