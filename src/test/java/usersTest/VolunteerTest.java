package usersTest;

import database.DatabaseCreation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.Request;
import request.Status;
import users.Client;
import users.Facilities;
import users.Validator;
import users.Volunteer;

import java.io.ByteArrayInputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class VolunteerTest {

    private Volunteer volunteer;
//    private Volunteer volunteerRefused;
//    private Volunteer volunteerAccepted;
    private Client client;
    private Client clientRefused;
    private Client clientAccepted;
    private Validator validatorSchool;
    private Validator validatorHospital;

    // Set up method to initialize test data before each test
    @BeforeEach
    void setUp() throws Exception {
        volunteer = Volunteer.createVolunteer("volunteerId", "volunteerPswd");
//        volunteerRefused = Volunteer.createVolunteer("volunteerRefusedId", "volunteerRefusedPswd");
//        volunteerAccepted = Volunteer.createVolunteer("volunteerAcceptedId", "volunteerAcceptedPswd");
        client = Client.createClient("clientId", "clientPswd", Facilities.RETIREMENT);

        clientRefused = Client.createClient("clientRefusedId", "clientRefusedPswd", Facilities.HOSPITAL);
        clientAccepted = Client.createClient("clientAcceptedId", "clientAcceptedPswd", Facilities.SCHOOL);
        validatorHospital = Validator.createValidator("validatorHospitalId", "validatorHospitalPswd", Facilities.HOSPITAL);
        validatorSchool = Validator.createValidator("validatorSchoolId", "validatorSchoolPswd", Facilities.SCHOOL);

        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("INSERT INTO request (idRequest, idSender, idDestination, message, status, facility) VALUES (?, ?, ?, ?, ?, ?)");
        statement.setInt(1, 1);
        statement.setString(2, client.getId());
        statement.setString(3, volunteer.getId());
        statement.setString(4, "Demande pour volontaire");
        statement.setString(5, "ACCEPTED");
        statement.setString(6, String.valueOf(client.getFacility()));
        statement.execute();
        Client.sendRequest(clientAccepted.getId(), "message", clientAccepted.getFacility());
        Client.sendRequest(clientRefused.getId(), "message", clientRefused.getFacility());
        System.setIn(new ByteArrayInputStream("y\n".getBytes()));
        validatorSchool.validate();
        System.setIn(new ByteArrayInputStream("y\n".getBytes()));
        validatorHospital.validate();
    }

    // Tear down method to clean up after each test
    @AfterEach
    void tearDown() throws SQLException {
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("DELETE FROM user WHERE id=?");
        statement.setString(1, "volunteerId");
        statement.execute();

        statement.setString(1, "clientId");
        statement.execute();

        statement.setString(1, "newVolunteerId");
        statement.execute();

        statement.setString(1, "volunteerAcceptedId");
        statement.execute();

        statement.setString(1, "volunteerRefusedId");
        statement.execute();

        statement.setString(1, "clientAcceptedId");
        statement.execute();

        statement.setString(1, "clientRefusedId");
        statement.execute();

        statement.setString(1, "validatorSchoolId");
        statement.execute();

        statement.setString(1, "validatorHospitalId");
        statement.execute();

        statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("DELETE FROM request WHERE idDestination=?");
        statement.setString(1, "volunteerId");
        statement.execute();

        statement.setString(1, "volunteerAcceptedId");
        statement.execute();
    }

    @Test
    void createVolunteer() {
        try {
            Volunteer newVolunteer = Volunteer.createVolunteer("newVolunteerId", "newVolunteerPswd");
            assertNotNull(newVolunteer);
            assertEquals("newVolunteerId", newVolunteer.getId());
            assertEquals("newVolunteerPswd", newVolunteer.getPswd());
        } catch (SQLException e) {
            fail("SQLException was thrown: " + e.getMessage());
        }
    }

    @Test
    void getUser() {
        try {
            Volunteer fetchedVolunteer = Volunteer.getUser("volunteerId");
            assertNotNull(fetchedVolunteer);
            assertEquals("volunteerId", fetchedVolunteer.getId());
            assertEquals("volunteerPswd", fetchedVolunteer.getPswd());
        } catch (SQLException e) {
            fail("SQLException was thrown: " + e.getMessage());
        }
    }

    @Test
    void getRequests() {
        try {
            List<Request> requests = volunteer.getRequests();
            assertNotNull(requests);
            assertFalse(requests.isEmpty());
            Request request = requests.get(0);
            assertEquals("clientId", request.getIdSender());
            assertEquals("Demande pour volontaire", request.getMessage());
            assertEquals(Facilities.RETIREMENT, request.getFacility());
            assertEquals(Status.ACCEPTED, request.getStatus());
            assertEquals("volunteerId", request.getIdDestination());
        } catch (SQLException e) {
            fail("SQLException was thrown: " + e.getMessage());
        }
    }

    @Test
    void seeAllRequests() throws SQLException {
        List<Request> requests = Volunteer.seeAllRequests();
        assertEquals(2, requests.size());
        Request request1 = requests.get(0);
        Request request2 = requests.get(1);
        assertEquals(clientAccepted.getId(), request1.getIdSender());
        assertEquals(clientRefused.getId(), request2.getIdSender());
    }

    //Test of the CLI
//    @Test
//    void chooseRequestAccepted() throws SQLException {
//        String simulatedInput = "y\nn\n";
//        Scanner scanner = new Scanner(new java.io.ByteArrayInputStream(simulatedInput.getBytes()));
//        volunteerAccepted.chooseRequest(scanner);
//        List<Request> requests = volunteerAccepted.getRequests();
//        assertNotNull(requests);
//        assertFalse(requests.isEmpty());
//        Request request = requests.get(0);
//        assertEquals(volunteerAccepted.getId(), request.getIdDestination());
//        assertEquals(Status.ACCEPTED, request.getStatus());
//    }
//
//    @Test
//    void chooseRequestRefused() throws SQLException {
//        String simulatedInput = "n\nn\n";
//        Scanner scanner = new Scanner(new java.io.ByteArrayInputStream(simulatedInput.getBytes()));
//        volunteerRefused.chooseRequest(scanner);
//        List<Request> requests = volunteerRefused.getRequests();
//        assertNotNull(requests);
//        assertTrue(requests.isEmpty());
//    }
}