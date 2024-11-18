import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private Client client;
    private Validator validator;
    private Volunteer volunteer;

    @BeforeEach
    void setUp() throws SQLException {
        client = Client.createClient("clientId", "clientPswd", Facilities.HOSPITAL);
        validator = Validator.createValidator("validatorId", "validatorPswd", Facilities.HOSPITAL);
        volunteer = Volunteer.createVolunteer("volunteerId", "volunteerPswd");
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("INSERT INTO review (idReview, idAuthor, title, content) VALUES (?, ?, ?, ?)");
        statement.setInt(1,1);
        statement.setString(2,validator.getId());
        statement.setString(3,"Validator Review");
        statement.setString(4,"This is the first review");
        statement.execute();
        statement.setInt(1,2);
        statement.setString(2, volunteer.getId());
        statement.setString(3,"Volunteer Review");
        statement.setString(4,"This is the second review");
        statement.execute();
        statement.setInt(1,3);
        statement.setString(2, volunteer.getId());
        statement.setString(3,"Volunteer Review bis");
        statement.setString(4,"This is the third review");
        statement.execute();
    }

    @AfterEach
    void tearDown() throws SQLException {
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("DELETE FROM user WHERE id=?");
        statement.setString(1, "clientId");
        statement.execute();
        statement.setString(1, "validatorId");
        statement.execute();
        statement.setString(1, "volunteerId");
        statement.execute();
    }

    @Test
    void sendReview() throws SQLException {
        Review review = User.sendReview(client, "Client Review", "This is the first review");
        assertNotNull(review);
        assertEquals("clientId", review.getIdAuthor());
        assertEquals("Client Review", review.getTitle());
        assertEquals("This is the first review", review.getContent());
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("SELECT * FROM review WHERE idAuthor = ?");
        statement.setString(1, "clientId");
        ResultSet resultSet = statement.executeQuery();
        assertTrue(resultSet.next());
        assertEquals("Client Review", resultSet.getString("title"));
        assertEquals("This is the first review", resultSet.getString("content"));
    }

    @Test
    void getReviews() throws SQLException {
        List<Review> reviews = User.getReviews();
        assertNotNull(reviews);
        assertEquals(3, reviews.size());
    }

    @Test
    void getMyReviews() throws SQLException {
        List<Review> ValidatorReviews = validator.getMyReviews();
        assertNotNull(ValidatorReviews);
        assertEquals(1, ValidatorReviews.size());
        assertEquals("validatorId", ValidatorReviews.get(0).getIdAuthor());
        List<Review> VolunteerReviews = volunteer.getMyReviews();
        assertNotNull(VolunteerReviews);
        assertEquals(2, VolunteerReviews.size());
        assertEquals("volunteerId", VolunteerReviews.get(0).getIdAuthor());
    }
}