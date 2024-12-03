package users;

import database.DatabaseCreation;
import request.Request;
import request.Status;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Client extends User {

    public Facilities facility;

    public Client(String id, String pswd, Facilities facility) {
        super(id,pswd);
        this.facility = facility ;
    }

    public Facilities getFacility() { return facility; }

    // Method to create a new client in the database
    public static Client createClient(String id, String pswd, Facilities facility) throws SQLException {
        // Check if the client ID already exists in the database
        PreparedStatement checkStatement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("SELECT COUNT(*) FROM client WHERE id = ?");
        checkStatement.setString(1, id);
        ResultSet resultSet = checkStatement.executeQuery();
        if (resultSet.next() && resultSet.getInt(1) > 0) {
            throw new SQLException("users.User " + id + " already exists");
        }

        // If the client ID does not exist, proceed to create the client
        User.createUser(id,pswd);
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("INSERT INTO client (id,facility) VALUES (?,?)");
        statement.setString(1, id);
        statement.setString(2,facility.toString());
        statement.execute();
        return new Client(id,pswd,facility);
    }

    // Method to retrieve a client by ID from the database
    public static Client getUser(String id) throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = DatabaseCreation.getInstance().getConnection()
                    .prepareStatement("SELECT * FROM user INNER JOIN client ON user.id = client.id WHERE user.id = ?");
            statement.setString(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String pswd = resultSet.getString("pswd");
                String facilityStr = resultSet.getString("facility");
                Facilities facility = Facilities.valueOf(facilityStr.toUpperCase());
                return new Client(id,pswd,facility);
            } else {
                throw new SQLException("users.User not found");
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

    // Method to send a request from the client
    public static Request sendRequest(String idSender, String message, Facilities facility) throws SQLException {
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("INSERT INTO request (idSender, message, facility, status) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, idSender);
        statement.setString(2, message);
        statement.setString(3, facility.toString());
        statement.setString(4, Status.WAITING.toString());
        statement.executeUpdate();

        // Retrieve the generated ID of the new request
        ResultSet generatedKeys = statement.getGeneratedKeys();
        generatedKeys.next();
        return Request.createRequest(generatedKeys.getInt(1), idSender, message, facility);
    }

    @Override
    // Method to retrieve a list of requests sent by the client, excluding completed requests
    public List<Request> getRequests() throws SQLException {
        // Prepare a SQL statement to retrieve requests from the client
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("SELECT * FROM request WHERE request.idSender = ? AND NOT status = ?");
        statement.setString(1, getId());
        statement.setString(2, Status.DONE.toString());
        ResultSet resultSet = statement.executeQuery();
        // Create a list to hold the client’s requests
        List<Request> requests = new ArrayList<>();
        while (resultSet.next()) {
            requests.add(new Request(resultSet.getInt("idRequest"),resultSet.getString("idSender"),
                    resultSet.getString("message"),
                    resultSet.getString("validatorMessage"),
                    resultSet.getString("motif"),
                    Facilities.valueOf(resultSet.getString("facility").toUpperCase()),
                    Status.valueOf(resultSet.getString("status").toUpperCase()),
                    resultSet.getString("idDestination")));
            }
        return requests;
    }
}