import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class RequestTest {

    private Client clientRequest;

    // Set up method to initialize test data before each test
    @BeforeEach
    void setUp() throws SQLException {
        clientRequest = Client.createClient("clientRequestId", "clientRequestPswd", Facilities.HOSPITAL);
        Volunteer volunteerRequest = Volunteer.createVolunteer("volunteerRequestId", "volunteerRequestPswd");
    }

    // Tear down method to clean up after each test
    @AfterEach
    void tearDown() throws SQLException {
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("DELETE FROM user WHERE id=?");
        statement.setString(1, "clientRequestId");
        statement.execute();

        statement.setString(1, "volunteerRequestId");
        statement.execute();

        statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("DELETE FROM request WHERE idSender=?");
        statement.setString(1, "clientRequestId");
        statement.execute();
    }

    @Test
    public void createRequest() {
        Request request = Request.createRequest(1, clientRequest.getId(), "Demande de test", clientRequest.getFacility());
        assertNotNull(request);
        assertEquals(1, request.getIdRequest());
        assertEquals(clientRequest.getId(), request.getIdSender());
        assertEquals("Demande de test", request.getMessage());
        assertEquals(clientRequest.getFacility(), request.getFacility());
    }

    @Test
    void save() throws SQLException {
        Request request = new Request(1, "clientRequestId", "message", "validator message",
                "motif", Facilities.HOSPITAL, Status.VALIDATED, "volunteerRequestId");
        request.save();

        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("SELECT * FROM request WHERE idRequest = ?");
        statement.setInt(1, 1);
        ResultSet resultSet = statement.executeQuery();

        assertTrue(resultSet.next());
        assertEquals("clientRequestId", resultSet.getString("idSender"));
        assertEquals("message", resultSet.getString("message"));
        assertEquals("validator message", resultSet.getString("validatorMessage"));
        assertEquals("motif", resultSet.getString("motif"));
        assertEquals("HOSPITAL", resultSet.getString("facility"));
        assertEquals("VALIDATED", resultSet.getString("status"));
        assertEquals("volunteerRequestId", resultSet.getString("idDestination"));
    }
}