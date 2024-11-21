import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Validator extends User{

    public Facilities facility;

    public Validator(String id, String pswd, Facilities facility) {
        super(id,pswd);
        this.facility = facility;
    }

    public Facilities getFacility() {
        return facility;
    }

    // Method to create a new Validator user in the database
    public static Validator createValidator(String id, String pswd, Facilities facility) throws SQLException {
        // Check if the user ID already exists in the validator table
        PreparedStatement checkStatement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("SELECT COUNT(*) FROM validator WHERE id = ?");
        checkStatement.setString(1, id);
        ResultSet resultSet = checkStatement.executeQuery();
        if (resultSet.next() && resultSet.getInt(1) > 0) {
            throw new SQLException("User" + id + "already exists");

        }

        // Create a new user and save the data
        User.createUser(id,pswd);
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                    .prepareStatement("INSERT INTO validator (id, facility) VALUES (?, ?)");
            statement.setString(1, id);
            statement.setString(2, facility.toString());
            statement.execute();
        statement.close();
        return new Validator(id,pswd,facility);
    }

    // Method to retrieve a Validator user from the database by ID
    public static Validator getUser(String id) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = DatabaseCreation.getInstance().getConnection()
                    .prepareStatement("SELECT * FROM user INNER JOIN validator ON user.id = validator.id WHERE user.id = ?");
            statement.setString(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String pswd = resultSet.getString("pswd");
                String facilityStr = resultSet.getString("facility");
                Facilities facility = Facilities.valueOf(facilityStr.toUpperCase());
                return new Validator(id, pswd, facility);
            } else {
                throw new SQLException("User not found");
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }

    // Method to attempt logging in a validator by checking his ID and password
    public int login(String id, String pswd) {
        int connected = 0;
        try {
            if (getUser(id).getId().equals(id) && getUser(id).getPswd().equals(pswd)) {
                connected = 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connected;
    };

    @Override
    // Method to retrieve requests that are linked to the validator's facility
    public List<Request> getRequests() throws SQLException {
        // SQL query to get requests for the specific facility with a "waiting" status
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("SELECT * FROM request WHERE facility = ? AND status = ?");

        statement.setString(1,getFacility().toString()); // en fonction de la facility du validator
        statement.setString(2, (String.valueOf(Status.WAITING)));
        ResultSet resultSet = statement.executeQuery();

        // List to store the requests
        List<Request> requests = new ArrayList<>();
        while (resultSet.next()) {
            requests.add(new Request(resultSet.getInt("idRequest"),
                    resultSet.getString("idSender"),
                    resultSet.getString("message"),
                    resultSet.getString("validatorMessage"),
                    resultSet.getString("motif"),
                    Facilities.valueOf(resultSet.getString("facility").toUpperCase()),
                    Status.valueOf(resultSet.getString("status").toUpperCase()),
                    resultSet.getString("idDestination")));
        }
        return requests;
    }

    // Method for the validator to approve or reject requests
    public boolean validate() throws Exception {
        boolean valid = false;
        List<Request> Requests = getRequests();
        if (!Requests.isEmpty()) {
            for (Request r : Requests) {
                if (r.getStatus() == Status.VALIDATED) {
                    // Skip already validated requests
                    System.out.println("\nThe request : " + r.toString() + " has already been approved .");
                    continue;
                }
                System.out.println("\n The request is : " + r.toString());
                Scanner scanner = new Scanner(System.in);
                System.out.println("\n Do you want to validate this request? y/n");
                String response = scanner.nextLine();
                if (response.equals("y")) {
                    r.setStatus(Status.VALIDATED);
                    r.save();
                    valid = true;
                } else if (response.equals("n")) {
                    r.setStatus(Status.REJECTED);
                    Scanner scannerValidatorMessage = new Scanner(System.in);
                    System.out.println("Please explain the reason behind your disapproval : ");
                    String validatorMessage = scannerValidatorMessage.nextLine();
                    r.setValidatorMessage(validatorMessage);
                    r.save();
                } else {
                    throw new Exception("The response should be y or n");
                }
            }
        }
        else{
            System.out.println("There is no Request to validate");
        }
        return valid;
    }
}