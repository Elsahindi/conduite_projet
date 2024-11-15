import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Main {
    public static void main(String[] args) {
        try {
            // Create a new validator and user in the database
            Client newClient = Client.getUser("MLAKii");
            Volunteer newVolunteer = Volunteer.getUser("anyaa");
            Validator newValidator = Validator.getUser("testid");
            System.out.println("Validator created: " + newValidator.getId() + ", Facility: " + newValidator.facility);
            Request request = Client.sendRequest("MLAKii","coucou ca vaaa",Facilities.HOSPITAL);
            System.out.println("validator getrequest: " + newValidator.getRequests());
            System.out.println("client getrequest" + newClient.getRequests());
            System.out.println("volunteer getrequest" + newVolunteer.getRequests());



            // Retrieve the validator from the database by id
            Validator fetchedValidator = newValidator.getUser("test_user2");
            System.out.println("Fetched Validator: " + fetchedValidator.getId() + ", Facility: " + fetchedValidator.facility);

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}


