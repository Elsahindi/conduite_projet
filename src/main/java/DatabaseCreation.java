import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseCreation {

    public DatabaseCreation() throws SQLException {

    Connection connection = DriverManager.getConnection("jdbc:mysql://srv-bdens.insa-toulouse.fr:3306/", "projet_gei_028", "Ka5yui6J");
    Statement statement = connection.createStatement();

    boolean e = statement.execute("use projet_gei_028");

    boolean executed = statement.execute(
            "CREATE TABLE user ( " +
                    "id VARCHAR(255), " +
                    "pswd VARCHAR(255) )"
    );

        if (executed) {
        System.out.println("Statement executed");
    } else {
        System.out.println("Statement not executed");
    }


    }
}
