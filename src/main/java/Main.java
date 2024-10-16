import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Main {
    public static void main(String[] args) {
        try {
            // Create a new validator and user in the database
            Validator newValidator = Validator.createValidator("test_user", "password123", Facilities.SCHOOL);
            System.out.println("Validator created: " + newValidator.getId() + ", Facility: " + newValidator.facility);

            // Retrieve the validator from the database by id
            Validator fetchedValidator = newValidator.getUser("test_user");
            System.out.println("Fetched Validator: " + fetchedValidator.getId() + ", Facility: " + fetchedValidator.facility);

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}


