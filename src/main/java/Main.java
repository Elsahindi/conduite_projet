import request.Request;
import users.Client;
import users.Facilities;
import users.Validator;
import users.Volunteer;

import java.sql.SQLException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        try {
            // Create a new validator and user in the database
            Client newClient = Client.getUser("testchoose1");
            Volunteer newVolunteer = Volunteer.getUser("anyaa");
            Validator newValidator = Validator.getUser("buddie");
            System.out.println("users.Client created : " + newClient.getId() + "Facility" + newClient.getFacility());
            System.out.println("users.Volunteer created : " + newVolunteer.getId());
            System.out.println("users.Validator created: " + newValidator.getId() + ", Facility: " + newValidator.facility);

            Request request = Client.sendRequest("testchoose1","coucou ca vaaa", Facilities.HOSPITAL);
            //System.out.println(request.getStatus());
            //request.setStatus(request.Status.VALIDATED);


            System.out.println(request.getStatus());
            newValidator.validate();
            Scanner scanner = new Scanner(System.in);
            newVolunteer.chooseRequest(scanner);

            //System.out.println("validator getrequest: " + newValidator.getRequests());
            //System.out.println("client getrequest" + newClient.getRequests());

            System.out.println("volunteer getrequest" + newVolunteer.getRequests());

            // Retrieve the validator from the database by id
            //users.Validator fetchedValidator = newValidator.getUser("test_user2");
            //System.out.println("Fetched users.Validator: " + fetchedValidator.getId() + ", Facility: " + fetchedValidator.facility);


        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


