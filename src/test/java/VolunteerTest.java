import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VolunteerTest {

    private Volunteer volunteer;

    @BeforeEach
    void setUp() throws SQLException {
        volunteer = Volunteer.createVolunteer("volunteerId", "volunteerPswd");

        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("INSERT INTO request (idSender, idDestination, message, status) VALUES (?, ?, ?, ?)");
        statement.setString(1, "clientId");
        statement.setString(2, "volunteerId");  // Liée au volontaire
        statement.setString(3, "Demande pour volontaire");
        statement.setString(4, "en attente");
        statement.execute();
    }

    @AfterEach
    void tearDown() throws SQLException {
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("DELETE FROM user WHERE id=?");
        statement.setString(1, "volunteerId");
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
            assertEquals("volunteerId", request.getIdDestination());
            assertEquals("Demande pour volontaire", request.getMessage());
            assertEquals("en attente", request.getStatus());
        } catch (SQLException e) {
            fail("SQLException was thrown: " + e.getMessage());
        }
    }
}