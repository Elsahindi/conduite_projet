import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ReviewTest {

    public String idAuthor = "author";
    public String title = "Test Review";
    public String content = "This is a test of the reviewm creation method.";

    // Tear down method to clean up after each test
    @AfterEach
    void tearDown() throws SQLException {
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("DELETE FROM user WHERE id=?");
        statement.setString(1, "author");
        statement.execute();
    }

    @Test
    public void createReview() {
        Review review = Review.createReview(1, idAuthor, title, content);
        assertNotNull(review);
        assertEquals(idAuthor, review.getidAuthor());
        assertEquals(title, review.getTitle());
        assertEquals(content, review.getContent());
    }

//    @Test
//    void save() throws SQLException {
//        Review review = Review.createReview(1, idAuthor, title, content);
//        review.save();
//        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
//                .prepareStatement("SELECT * FROM review WHERE idReview = ?");
//        statement.setInt(1, 1);
//        ResultSet resultSet = statement.executeQuery();
//
//        assertTrue(resultSet.next());
//        assertEquals(review.getidAuthor(), resultSet.getString("idAuthor"));
//        assertEquals(review.getTitle(), resultSet.getString("title"));
//        assertEquals(review.getContent(), resultSet.getString("content"));
//    }
}