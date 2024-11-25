package usersTest;

import database.DatabaseCreation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.Request;
import request.Status;
import users.Client;
import users.Facilities;
import users.Validator;
import users.Volunteer;

import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    private Validator validator;
    private Client client;
    private Volunteer destination;

    // Set up method to initialize test data before each test
    @BeforeEach
    void setUp() throws SQLException {
        validator = Validator.createValidator("validatorId", "validatorPswd", Facilities.HOSPITAL);
        client = Client.createClient("clientId", "clientPswd", Facilities.HOSPITAL);
        destination = Volunteer.createVolunteer("destinationId", "DestinationPswd");
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("INSERT INTO request (idRequest, idSender, idDestination, message, status, facility) VALUES (?, ?, ?, ?, ?, ?)");
        statement.setInt(1, 1);
        statement.setString(2, client.getId());
        statement.setString(3, destination.getId());
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
        statement.setString(1, "validatorId");
        statement.execute();
        statement.setString(1, "clientId");
        statement.execute();
        statement.setString(1, "destinationId");
        statement.execute();
        statement.setString(1, "elsa-super-java");
        statement.execute();

        statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("DELETE FROM request WHERE idSender = ?");
        statement.setString(1, "clientId");
        statement.execute();
    }

    @Test
    void createValidator() {
        try {
            Validator newValidator = Validator.createValidator("elsa-super-java", "romain-le-chien", Facilities.RETIREMENT);
            assertNotNull(newValidator);
            assertEquals("elsa-super-java", newValidator.getId());
            assertEquals("romain-le-chien", newValidator.getPswd());
            assertEquals(Facilities.RETIREMENT, newValidator.facility);
        } catch (SQLException e) {
            fail("SQLException was thrown: " + e.getMessage());
        }
    }

    @Test
    void getUser() {
        try {
            Validator fetchedValidator = validator.getUser("validatorId");
            assertNotNull(fetchedValidator);
            assertEquals("validatorId", fetchedValidator.getId());
            assertEquals("validatorPswd", fetchedValidator.getPswd());
            assertEquals(Facilities.HOSPITAL, fetchedValidator.facility);
        } catch (SQLException e) {
            fail("SQLException was thrown: " + e.getMessage());
        }
    }

    @Test
    void login() {
        assertDoesNotThrow(() -> validator.login("validatorId", "validatorPswd"));
        assertThrows(RuntimeException.class, () -> validator.login("wrongId", "wrongPswd"));
    }

    @Test
    void getRequests() {
        try {
            List<Request> requests = validator.getRequests();
            assertNotNull(requests);
            if (!requests.isEmpty()) {
                Request request = requests.get(0);
                assertEquals(Facilities.HOSPITAL, request.getFacility());
            };
        } catch (SQLException e) {
            fail("SQLException was thrown: " + e.getMessage());
        }
    }

    @Test
    void validate() {
        try {
            System.setIn(new ByteArrayInputStream("y\n".getBytes()));
            assertTrue(validator.validate());
            PreparedStatement checkStatement = DatabaseCreation.getInstance().getConnection()
                    .prepareStatement("SELECT status FROM request WHERE idSender = ? AND message = ?");
            checkStatement.setString(1, "clientId");
            checkStatement.setString(2, "Demande de test");
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next()) {
               assertEquals(Status.VALIDATED.toString(), resultSet.getString("status"));
            }
        } catch (Exception e) {
            fail("Exception was thrown: " + e.getMessage());
        }
    }
}