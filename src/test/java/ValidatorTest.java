import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        validator = new Validator("clientId", "clientPswd", Facilities.HOSPITAL);
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
}