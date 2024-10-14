import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseCreation {

    private static DatabaseCreation instance;
    private Connection connection;

    public Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/projet_gei_028", "projet_gei_028", "Ka5yui6J");
        }
        return connection;
    }
    public static DatabaseCreation getInstance(){
        if (instance == null) {
            instance = new DatabaseCreation();
        }
        return instance;
    }

}