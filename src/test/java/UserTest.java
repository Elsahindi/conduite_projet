import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;
    //User est une classe abstraite donc il faut créer une classe concrète pour pouvoir la tester!
    //Est-ce vraiment dans notre intérêt de tester une classe abstraite alors qu'on teste tous son héritage??
    // Si on l'efface --> il faut traiter tout dans les autres hummmmmmmm (meme celles pas modifiées)
    static class ConcreteUser extends User {

        public ConcreteUser(String id, String pswd) {
            super(id, pswd);
        }

        @Override
        public User getUser(String id) {
            return new ConcreteUser(id, "testPswd");
        }

        @Override
        public void login(String id, String pswd) {
            if (this.id.equals(id) && this.pswd.equals(pswd)) {
                System.out.println("You have been log successfully!");
            } else {
                throw new RuntimeException("Login failed");
            }
        }
    }

    @BeforeEach
    void setUp() {
        user = new ConcreteUser("testId", "testPswd");
    }

    @Test
    void getId() {
        assertEquals("testId", user.getId());
    }

    @Test
    void getPswd() {
        assertEquals("testPswd", user.getPswd());
    }

    @Test
    void createUser() {
        try {
            User.createUser("newUser", "newPswd");
            assertNotNull(user.getUser("newUser"));
        } catch (SQLException e) {
            fail("SQLException was thrown: " + e.getMessage());
        }
    }

    @Test
    void login() {
        assertDoesNotThrow(() -> user.login("testId", "testPswd"));
        assertThrows(RuntimeException.class, () -> user.login("wrongId", "wrongPswd"));
    }
}
