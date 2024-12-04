package usersTest;

import database.DatabaseCreation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import review.Review;
import users.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private static final String CLIENT_ID = "clientId";
    private static final String VALIDATOR_ID = "validatorId";
    private static final String VOLUNTEER_ID = "volunteerId";

    private Client client;
    private Validator validator;
    private Volunteer volunteer;

    // Set up method to initialize test data before each test
    @BeforeEach
    void setUp() throws SQLException {
        client = Client.createClient(CLIENT_ID, "clientPswd", Facilities.HOSPITAL);
        validator = Validator.createValidator(VALIDATOR_ID, "validatorPswd", Facilities.HOSPITAL);
        volunteer = Volunteer.createVolunteer(VOLUNTEER_ID, "volunteerPswd");
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("INSERT INTO review (idReview, idAuthor, title, content) VALUES (?, ?, ?, ?)");
        statement.setInt(1,1);
        statement.setString(2,VALIDATOR_ID);
        statement.setString(3,"users.Validator review.Review");
        statement.setString(4,"This is the first review");
        statement.execute();
        statement.setInt(1,2);
        statement.setString(2, VOLUNTEER_ID);
        statement.setString(3,"users.Volunteer review.Review");
        statement.setString(4,"This is the second review");
        statement.execute();
        statement.setInt(1,3);
        statement.setString(2, VOLUNTEER_ID);
        statement.setString(3,"users.Volunteer review.Review bis");
        statement.setString(4,"This is the third review");
        statement.execute();
    }

    // Tear down method to clean up after each test
    @AfterEach
    void tearDown() throws SQLException {
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("DELETE FROM user WHERE id=?");
        statement.setString(1, CLIENT_ID);
        statement.execute();
        statement.setString(1, VALIDATOR_ID);
        statement.execute();
        statement.setString(1, VOLUNTEER_ID);
        statement.execute();
    }

    @Test
    void login() {
        assertDoesNotThrow(() -> User.login(CLIENT_ID, "clientPswd"));
        assertThrows(RuntimeException.class, () -> User.login("wrongId", "wrongPswd"));

        assertDoesNotThrow(() -> User.login(VALIDATOR_ID, "validatorPswd"));
        assertThrows(RuntimeException.class, () -> User.login("wrongId", "wrongPswd"));

        assertDoesNotThrow(() -> User.login(VOLUNTEER_ID, "volunteerPswd"));
        assertThrows(RuntimeException.class, () -> User.login("wrongId", "wrongPswd"));
    }

    @Test
    void sendReview() throws SQLException {
        Review review = User.sendReview(client, "Client review", "This is the first review");
        assertNotNull(review);
        assertEquals(CLIENT_ID, review.getIdAuthor());
        assertEquals("Client review", review.getTitle());
        assertEquals("This is the first review", review.getContent());
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("SELECT * FROM review WHERE idAuthor = ?");
        statement.setString(1, "clientId");
        ResultSet resultSet = statement.executeQuery();
        assertTrue(resultSet.next());
        assertEquals("Client review", resultSet.getString("title"));
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
        assertEquals(VALIDATOR_ID, ValidatorReviews.get(0).getIdAuthor());
        List<Review> VolunteerReviews = volunteer.getMyReviews();
        assertNotNull(VolunteerReviews);
        assertEquals(2, VolunteerReviews.size());
        assertEquals(VOLUNTEER_ID, VolunteerReviews.get(0).getIdAuthor());
    }
}