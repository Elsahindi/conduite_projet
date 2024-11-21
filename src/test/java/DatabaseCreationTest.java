import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseCreationTest {

    private DatabaseCreation databaseCreation;

    @Test
    void getConnection() throws SQLException {
        databaseCreation = DatabaseCreation.getInstance();
        Connection connection = databaseCreation.getConnection();
        assertNotNull(connection, "Connection should not be null");
        assertFalse(connection.isClosed(), "Connection should be open");
    }

    // to test if we hhave only one instance of connection
    @Test
    void getInstance() {
        DatabaseCreation instance1 = DatabaseCreation.getInstance();
        DatabaseCreation instance2 = DatabaseCreation.getInstance();
        assertSame(instance1, instance2, "It should be only one DatabaseCreation");
    }
}
