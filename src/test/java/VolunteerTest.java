import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VolunteerTest {

    private Volunteer volunteer;
    private Client client;

    @BeforeEach
    void setUp() throws SQLException {
        volunteer = Volunteer.createVolunteer("volunteerId", "volunteerPswd");
        client = Client.createClient("clientId", "clientPswd", Facilities.RETIREMENT);

        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("INSERT INTO request (idRequest, idSender, idDestination, message, status, facility) VALUES (?, ?, ?, ?, ?, ?)");
        statement.setInt(1, 1);
        statement.setString(2, client.getId());
        statement.setString(3, volunteer.getId());
        statement.setString(4, "Demande pour volontaire");
        statement.setString(5, "WAITING");
        statement.setString(6, client.getFacility());
        statement.execute();
    }

    @AfterEach
    void tearDown() throws SQLException {
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("DELETE FROM user WHERE id=?");
        statement.setString(1, "volunteerId");
        statement.execute();

        statement.setString(1, "clientId");
        statement.execute();

        statement.setString(1, "newVolunteerId");
        statement.execute();

        statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("DELETE FROM request WHERE idDestination=?");
        statement.setString(1, "volunteerId");
        statement.execute();
    }

    @Test
    void createVolunteer() {
        try {
            Volunteer newVolunteer = Volunteer.createVolunteer("newVolunteerId", "newVolunteerPswd");
            assertNotNull(newVolunteer);
            assertEquals("newVolunteerId", newVolunteer.getId());
            assertEquals("newVolunteerPswd", newVolunteer.getPswd());
        } catch (SQLException e) {
            fail("SQLException was thrown: " + e.getMessage());
        }
    }

    @Test
    void getUser() {
        try {
            Volunteer fetchedVolunteer = volunteer.getUser("volunteerId");
            assertNotNull(fetchedVolunteer);
            assertEquals("volunteerId", fetchedVolunteer.getId());
            assertEquals("volunteerPswd", fetchedVolunteer.getPswd());
        } catch (SQLException e) {
            fail("SQLException was thrown: " + e.getMessage());
        }
    }

    @Test
    void login() {
        assertDoesNotThrow(() -> volunteer.login("volunteerId", "volunteerPswd"));
        assertThrows(RuntimeException.class, () -> volunteer.login("wrongId", "wrongPswd"));
    }

    @Test
    void getRequests() {
        try {
            List<Request> requests = volunteer.getRequests();
            assertNotNull(requests);
            assertFalse(requests.isEmpty());
            Request request = requests.get(0);
            assertEquals("clientId", request.getIdSender());
            assertEquals("Demande pour volontaire", request.getMessage());
            assertEquals(Facilities.RETIREMENT, request.getFacility());
            assertEquals(Status.WAITING, request.getStatus());
            assertEquals("volunteerId", request.getIdDestination());
        } catch (SQLException e) {
            fail("SQLException was thrown: " + e.getMessage());
        }
    }
}