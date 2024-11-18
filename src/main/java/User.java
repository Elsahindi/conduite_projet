import javax.xml.crypto.Data;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

abstract class User {

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

    public static void createUser(String id, String pswd) throws SQLException {

        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("INSERT INTO user (id, pswd) VALUES (?,?)");

        statement.setString(1, id);
        statement.setString(2, pswd);

        statement.execute();
    }

    public abstract int login(String id, String pswd);

    public abstract List<Request> getRequests() throws SQLException;


    //méthode qui permet à un utilisateur de poster un avis sur l'application
    public static Review sendReview(User Author, String title, String content) throws SQLException {
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("INSERT INTO review (idAuthor, title, content) VALUES (?,?,?)");

        statement.setString(1,Author.getId());
        statement.setString(2,title );
        statement.setString(3,content);

        statement.executeUpdate();

        return Review.createReview(Author.getId(), title,content);
    }

    //permet d'accéder à tous les reviews
    public static List<Review> getReviews() throws SQLException {
        List<Review> reviews = new ArrayList<Review>();
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("SELECT * FROM review");

        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            reviews.add(new Review(resultSet.getString("idAuthor"),
                    resultSet.getString("title"),
                    resultSet.getString("content")));

        }
        return reviews;
    }

    //permet au user d'accéder uniquement à ses propres reviews
    public List<Review> getMyReviews() throws SQLException {
        List<Review> reviews = getReviews();  // Retrieve all reviews

        // Use an iterator to safely remove elements while iterating
        Iterator<Review> iterator = reviews.iterator();
        while (iterator.hasNext()) {
            Review review = iterator.next();

            // If the idAuthor of the review is not the current user, remove it from the list
            if (!review.getidAuthor().equals(getId())) {
                iterator.remove();
            }
        }
        return reviews;  // Return the filtered list
    }
}
