package usersTest;

import database.DatabaseCreation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.Request;
import request.Status;
import users.Client;
import users.Facilities;
import users.Volunteer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    private Client client;
    private Client clientRequest;
    private Volunteer destination;

    // Set up method to initialize test data before each test
    @BeforeEach
    void setUp() throws SQLException {
        client = Client.createClient("clientId", "clientPswd", Facilities.HOSPITAL);
        clientRequest = Client.createClient("clientRequestId", "clientRequestPswd", Facilities.HOSPITAL);
        destination = Volunteer.createVolunteer("destinationId", "DestinationPswd");
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("INSERT INTO request (idRequest, idSender, idDestination, message, status, facility) VALUES (?, ?, ?, ?, ?, ?)");
        statement.setInt(1, 1);
        statement.setString(2, clientRequest.getId());
        statement.setString(3, destination.getId());
        statement.setString(4, "Demande de test");
        statement.setString(5, "WAITING");
        statement.setString(6, String.valueOf(clientRequest.getFacility()));
        statement.execute();
    }

    // Tear down method to clean up after each test
    @AfterEach
    void tearDown() throws SQLException {
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("DELETE FROM user WHERE id=?");
        statement.setString(1, "clientId");
        statement.execute();

        statement.setString(1, "newClientId");
        statement.execute();

        statement.setString(1, "clientRequestId");
        statement.execute();

        statement.setString(1, "destinationId");
        statement.execute();

        statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("DELETE FROM request WHERE idSender=?");
        statement.setString(1, "clientRequestId");
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
            Client fetchedClient = client.getUser("clientId");
            assertNotNull(fetchedClient);
            assertEquals("clientId", fetchedClient.getId());
            assertEquals("clientPswd", fetchedClient.getPswd());
            assertEquals(Facilities.HOSPITAL, fetchedClient.facility);
        } catch (SQLException e) {
            fail("SQLException was thrown: " + e.getMessage());
        }
    }

    @Test
    void login() {
        assertDoesNotThrow(() -> client.login("clientId", "clientPswd"));
        assertThrows(RuntimeException.class, () -> client.login("wrongId", "wrongPswd"));
    }

    @Test
    void sendRequest() throws SQLException {
        try{
            String message = "Nouvelle demande pour validation";
            Facilities facility = Facilities.HOSPITAL;
            Client.sendRequest("clientId", message, facility);

            PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                    .prepareStatement("SELECT * FROM request WHERE idSender = ? AND message = ?");
            statement.setString(1, "clientId");
            statement.setString(2, message);

            ResultSet resultSet = statement.executeQuery();
            assertTrue(resultSet.next());
            assertEquals("clientId", resultSet.getString("idSender"));
            assertEquals(message, resultSet.getString("message"));
            assertEquals(facility.name(), resultSet.getString("facility"));
            assertEquals("WAITING", resultSet.getString("status"));

    } catch (SQLException e) {
        fail("SQLException was thrown: " + e.getMessage());}
    }

    @Test
    void getRequests() {
        try {
            List<Request> requests = clientRequest.getRequests();
            assertNotNull(requests);
            assertFalse(requests.isEmpty());
            Request request = requests.get(0);
            assertEquals("clientRequestId", request.getIdSender());
            assertEquals("Demande de test", request.getMessage());
            assertEquals(Facilities.HOSPITAL, request.getFacility());
            assertEquals(Status.WAITING, request.getStatus());
        } catch (SQLException e) {
            fail("SQLException was thrown: " + e.getMessage());
        }
    }
}