package usersTest;

import database.DatabaseCreation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.Request;
import request.Status;
import users.*;

import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    private static final String CLIENT_ID = "clientId";
    private static final String VALIDATOR_ID = "validatorId";
    private static final String VOLUNTEER_ID = "destinationId";
    private static final String WAITING_STATUS = "WAITING";
    private static final String VALIDATED_STATUS = Status.VALIDATED.toString();
    private Validator validator;
    private Client client;
    private Volunteer destination;

    // Set up method to initialize test data before each test
    @BeforeEach
    void setUp() throws SQLException {
        validator = Validator.createValidator(VALIDATOR_ID, "validatorPswd", Facilities.HOSPITAL);
        client = Client.createClient(CLIENT_ID, "clientPswd", Facilities.HOSPITAL);
        destination = Volunteer.createVolunteer(VOLUNTEER_ID, "DestinationPswd");
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("INSERT INTO request (idRequest, idSender, idDestination, message, status, facility) VALUES (?, ?, ?, ?, ?, ?)");
        statement.setInt(1, 1);
        statement.setString(2, CLIENT_ID);
        statement.setString(3, VOLUNTEER_ID);
        statement.setString(4, "Demande de test");
        statement.setString(5, "WAITING");
        statement.setString(6, String.valueOf(client.getFacility()));
        statement.execute();
    }

    // Tear down method to clean up after each test
    @AfterEach
    void tearDown() throws SQLException {
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("DELETE FROM user WHERE id=?");
        statement.setString(1, VALIDATOR_ID);
        statement.execute();
        statement.setString(1, CLIENT_ID);
        statement.execute();
        statement.setString(1, VOLUNTEER_ID);
        statement.execute();
        statement.setString(1, "new_validatorId");
        statement.execute();

        statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("DELETE FROM request WHERE idSender = ?");
        statement.setString(1, CLIENT_ID);
        statement.execute();
    }

    @Test
    void createValidator() {
        try {
            Validator newValidator = Validator.createValidator("new_validatorId", "new_validatorpswd", Facilities.RETIREMENT);
            assertNotNull(newValidator);
            assertEquals("new_validatorId", newValidator.getId());
            assertEquals("new_validatorpswd", newValidator.getPswd());
            assertEquals(Facilities.RETIREMENT, newValidator.facility);
        } catch (SQLException e) {
            fail("SQLException was thrown: " + e.getMessage());
        }
    }

    @Test
    void getUser() {
        try {
            Validator fetchedValidator = validator.getUser(VALIDATOR_ID);
            assertNotNull(fetchedValidator);
            assertEquals(VALIDATOR_ID, fetchedValidator.getId());
            assertEquals("validatorPswd", fetchedValidator.getPswd());
            assertEquals(Facilities.HOSPITAL, fetchedValidator.facility);
        } catch (SQLException e) {
            fail("SQLException was thrown: " + e.getMessage());
        }
    }

    @Test
    void login() {
        assertDoesNotThrow(() -> User.login(VALIDATOR_ID, "validatorPswd"));
        assertThrows(RuntimeException.class, () -> User.login("wrongId", "wrongPswd"));
    }

    @Test
    void getRequests() {
        try {
            List<Request> requests = validator.getRequests();
            assertNotNull(requests);
            if (!requests.isEmpty()) {
                Request request = requests.get(0);
                assertEquals(Facilities.HOSPITAL, request.getFacility());
            }
        } catch (SQLException e) {
            fail("SQLException was thrown: " + e.getMessage());
        }
    }

    private void assertValidRequestStatus(String senderId, String message, String expectedStatus) throws SQLException {
        try (PreparedStatement checkStatement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("SELECT status FROM request WHERE idSender = ? AND message = ?")) {
            checkStatement.setString(1, senderId);
            checkStatement.setString(2, message);
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next()) {
                assertEquals(expectedStatus, resultSet.getString("status"));
            }
        }
    }

    // Testing of the CLI
//    @Test
//    void validate() {
//        try {
//            System.setIn(new ByteArrayInputStream("y\n".getBytes()));
//            assertTrue(validator.validate());
//            assertValidRequestStatus(CLIENT_ID, "Demande de test", VALIDATED_STATUS);
//        } catch (Exception e) {
//            fail("Exception was thrown: " + e.getMessage());
//        }
//    }
}