package reviewTest;

import database.DatabaseCreation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import review.Review;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ReviewTest {

    public String idAuthor = "author";
    public String title = "Test review.Review";
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
    
}