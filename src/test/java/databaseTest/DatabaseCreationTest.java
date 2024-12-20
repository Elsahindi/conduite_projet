package databaseTest;

import database.DatabaseCreation;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseCreationTest {

    @Test
    void testGetConnection() throws SQLException {
        DatabaseCreation databaseCreation = DatabaseCreation.getInstance();
        Connection connection = databaseCreation.getConnection();
        assertNotNull(connection, "Connection should not be null");
        assertFalse(connection.isClosed(), "Connection should be open");
    }

    // to test if we have only one instance of connection
    @Test
    void testSingletonInstance() {
        DatabaseCreation instance1 = DatabaseCreation.getInstance();
        DatabaseCreation instance2 = DatabaseCreation.getInstance();
        assertSame(instance1, instance2, "It should be only one database.DatabaseCreation");
    }
}
