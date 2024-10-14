import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    @Test
    void createValidator() {
        Validator a = new Validator("elsa-super-java", "romain-le-chien", Facilities.RETIREMENT);
        //assertTrue(a);
    }

    @Test
    void login() {
    }
}