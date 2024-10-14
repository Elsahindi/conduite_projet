import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) throws SQLException {
        Validator.createValidator("elsa-super-java", "romain-le-chien", Facilities.RETIREMENT);
    }
}
