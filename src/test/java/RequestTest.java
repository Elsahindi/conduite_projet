import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class RequestTest {

    private Client clientRequest;

    @BeforeEach
    void setUp() throws SQLException {
        clientRequest = Client.createClient("clientRequestId", "clientRequestPswd", Facilities.HOSPITAL);
    }

    @AfterEach
    void tearDown() throws SQLException {
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("DELETE FROM user WHERE id=?");
        statement.setString(1, "clientRequestId");
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
}