import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    private Client client;

    @BeforeEach
    void setUp() throws SQLException {
        client = Client.createClient("clientId", "clientPswd", Facilities.HOSPITAL);
    }

    @AfterEach
    void tearDown() throws SQLException {
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("DELETE FROM user WHERE id=?");
        statement.setString(1, "clientId");
        statement.execute();

        statement.setString(1, "newClientId");
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
}
