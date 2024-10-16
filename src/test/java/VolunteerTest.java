import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

class VolunteerTest {

    private Volunteer volunteer;

    @BeforeEach
    void setUp() {
        volunteer = new Volunteer("volunteerId", "volunteerPswd");
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
}