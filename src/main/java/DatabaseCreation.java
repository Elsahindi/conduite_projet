import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseCreation {

    private static DatabaseCreation instance;
    private Connection connection;

    public DatabaseCreation() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/", "projet_gei_028", "Ka5yui6J");
        this.connection = connection;
    }


    public Connection getConnection() {
        return connection;
    }
    public static DatabaseCreation getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseCreation();
        }

        return instance;
    }



}
