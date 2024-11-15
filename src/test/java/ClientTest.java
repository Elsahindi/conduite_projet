import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    private Client client;

    @BeforeEach
    void setUp() throws SQLException {
        client = Client.createClient("clientId", "clientPswd", Facilities.HOSPITAL);

        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("INSERT INTO request (idSender, message, facility, status) VALUES (?, ?, ?, ?)");
        statement.setString(1, "clientId");
        statement.setString(2, "Demande de test");
        statement.setString(3, "HOSPITAL");
        statement.setString(4, "WAITING");
        statement.execute();
    }

    @AfterEach
    void tearDown() throws SQLException {
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("DELETE FROM user WHERE id=?");
        statement.setString(1, "clientId");
        statement.execute();

        statement.setString(1, "newClientId");
        statement.execute();

        statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("DELETE FROM request WHERE idSender=?");
        statement.setString(1, "clientId");
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
    void sendRequest() {
        try{
            String message = "Nouvelle demande pour validation";
            Facilities facility = Facilities.HOSPITAL;
            //Request request = new Request("clientId", message, facility);
            //client.sendRequest(request);

            PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                    .prepareStatement("SELECT * FROM request WHERE idSender = ? AND message = ?");
            statement.setString(1, "clientId");
            statement.setString(2, message);
            ResultSet resultSet = statement.executeQuery();

            assertTrue(resultSet.next(), "La requête n'a pas été enregistrée dans la base de données.");
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
            List<Request> requests = client.getRequests();
            assertNotNull(requests);
            assertFalse(requests.isEmpty());
            Request request = requests.get(0);
            assertEquals("clientId", request.getIdSender());
            assertEquals("Demande de test", request.getMessage());
            assertEquals(Facilities.HOSPITAL, request.getFacility());
            assertEquals(Status.WAITING, request.getStatus());
        } catch (SQLException e) {
            fail("SQLException was thrown: " + e.getMessage());
        }
    }
}
