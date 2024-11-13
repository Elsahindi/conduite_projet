import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    private Validator validator;

    @BeforeEach
    void setUp() throws SQLException {
        //validator = new Validator("validatorId", "validatorPswd", Facilities.HOSPITAL);
        validator = Validator.createValidator("validatorId", "validatorPswd", Facilities.HOSPITAL);
    }

    @AfterEach
    void tearDown() throws SQLException {
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("DELETE FROM user WHERE id=?");
        statement.setString(1, "validatorId");
        statement.execute();

        statement.setString(1, "elsa-super-java");
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
        // !!!!!!! JE L'AI FAIT SANS CO A LA BASE DE DONNEE DONC A TESTER
    void getRequests() {
        try {
            List<Request> requests = validator.getRequests();
            assertNotNull(requests);
            assertFalse(requests.isEmpty());
            Request request = requests.get(0);
            assertEquals("validatorId", request.getIdDestination());
            assertEquals(Facilities.HOSPITAL, request.getFacility());
        } catch (SQLException e) {
            fail("SQLException was thrown: " + e.getMessage());
        }
    }
}