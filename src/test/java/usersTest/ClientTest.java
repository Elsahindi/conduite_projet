package usersTest;

import database.DatabaseCreation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.Request;
import request.Status;
import users.Client;
import users.Facilities;
import users.User;
import users.Volunteer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {
    private static final String CLIENT_ID = "clientId";
    private static final String CLIENT_REQUEST_ID = "clientRequestId";
    private static final String DESTINATION_ID = "destinationId";
    private static final String REQUEST_MESSAGE = "Demande de test";

    private Client client;
    private Client clientRequest;
    private Volunteer destination;

    // Set up method to initialize test data before each test
    @BeforeEach
    void setUp() throws SQLException {
        client = Client.createClient(CLIENT_ID, "clientPswd", Facilities.HOSPITAL);
        clientRequest = Client.createClient(CLIENT_REQUEST_ID, "clientRequestPswd", Facilities.HOSPITAL);
        destination = Volunteer.createVolunteer(DESTINATION_ID, "DestinationPswd");
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("INSERT INTO request (idRequest, idSender, idDestination, message, status, facility) VALUES (?, ?, ?, ?, ?, ?)");
        statement.setInt(1, 1);
        statement.setString(2, clientRequest.getId());
        statement.setString(3, destination.getId());
        statement.setString(4, REQUEST_MESSAGE);
        statement.setString(5, "WAITING");
        statement.setString(6, String.valueOf(clientRequest.getFacility()));
        statement.execute();
    }

    // Tear down method to clean up after each test
    @AfterEach
    void tearDown() throws SQLException {
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("DELETE FROM user WHERE id=?");
        statement.setString(1, CLIENT_ID);
        statement.execute();

        statement.setString(1, "newClientId");
        statement.execute();

        statement.setString(1, CLIENT_REQUEST_ID);
        statement.execute();

        statement.setString(1, DESTINATION_ID);
        statement.execute();

        statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("DELETE FROM request WHERE idSender=?");
        statement.setString(1, CLIENT_REQUEST_ID);
        statement.execute();
    }

    @Test
    void createClient() {
        try {
            Client newClient = Client.createClient("newClientId", "newClientPswd", Facilities.SCHOOL);
            assertNotNull(newClient);
            assertEquals("newClientId", newClient.getId());
            assertEquals("newClientPswd", newClient.getPswd());
            assertEquals(Facilities.SCHOOL, newClient.facility);
        } catch (SQLException e) {
            fail("SQLException was thrown: " + e.getMessage());
        }
    }

    @Test
    void getUser() {
        try {
            Client fetchedClient = Client.getUser(CLIENT_ID);
            assertNotNull(fetchedClient);
            assertEquals(CLIENT_ID, fetchedClient.getId());
            assertEquals("clientPswd", fetchedClient.getPswd());
            assertEquals(Facilities.HOSPITAL, fetchedClient.facility);
        } catch (SQLException e) {
            fail("SQLException was thrown: " + e.getMessage());
        }
    }

    @Test
    void login() {
        assertDoesNotThrow(() -> User.login(CLIENT_ID, "clientPswd"));
        assertThrows(RuntimeException.class, () -> User.login("wrongId", "wrongPswd"));
    }

    @Test
    void sendRequest() throws SQLException {
        String message = "Nouvelle demande pour validation";
        Facilities facility = Facilities.HOSPITAL;

        Client.sendRequest(CLIENT_ID, message, facility);

        try (PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("SELECT * FROM request WHERE idSender = ? AND message = ?")) {
            statement.setString(1, CLIENT_ID);
            statement.setString(2, message);

            ResultSet resultSet = statement.executeQuery();
            assertTrue(resultSet.next());
            assertEquals(CLIENT_ID, resultSet.getString("idSender"));
            assertEquals(message, resultSet.getString("message"));
            assertEquals(facility.name(), resultSet.getString("facility"));
            assertEquals("WAITING", resultSet.getString("status"));
        }
    }

    @Test
    void getRequests() {
        try {
            List<Request> requests = clientRequest.getRequests();
            assertNotNull(requests);
            assertFalse(requests.isEmpty());
            Request request = requests.get(0);
            assertEquals(CLIENT_REQUEST_ID, request.getIdSender());
            assertEquals(REQUEST_MESSAGE, request.getMessage());
            assertEquals(Facilities.HOSPITAL, request.getFacility());
            assertEquals(Status.WAITING, request.getStatus());
        } catch (SQLException e) {
            fail("SQLException was thrown: " + e.getMessage());
        }
    }
}