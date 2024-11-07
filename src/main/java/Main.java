import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Main {
    public static void main(String[] args) {
        try {
            // Create a new validator and user in the database
            Client newClient = Client.createClient("ML","123",Facilities.HOSPITAL);
            Validator newValidator = Validator.createValidator("test_user45567", "password1234", Facilities.SCHOOL);
            System.out.println("Validator created: " + newValidator.getId() + ", Facility: " + newValidator.facility);
            Request request = new Request("ML","coucou ca va",Facilities.HOSPITAL);
            Client.sendRequest(request);

            // Retrieve the validator from the database by id
            Validator fetchedValidator = newValidator.getUser("test_user2");
            System.out.println("Fetched Validator: " + fetchedValidator.getId() + ", Facility: " + fetchedValidator.facility);

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}


