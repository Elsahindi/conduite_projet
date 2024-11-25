package request;

import database.DatabaseCreation;
import users.Facilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Request {

    private Integer idRequest;
    private String idSender;
    private String idDestination;
    private String message;
    private String validatorMessage;
    private String motif;
    private Status status;
    private Facilities facility;

    public Request(int idRequest, String idSender, String message, Facilities facility) {

        this.idRequest = idRequest;
        this.idSender = idSender;
        this.message = message;
        this.facility = facility;

    }
    public Request(int idRequest, String idSender, String message, String validatorMessage, String motif, Facilities facility, Status status, String idDestination) {

        this.idRequest = idRequest;
        this.idSender = idSender;
        this.message = message;
        this.validatorMessage = validatorMessage;
        this.motif = motif;
        this.facility = facility;
        this.status = status;
        this.idDestination = idDestination;

    }

    public int getIdRequest() {return idRequest;}
    public void setIdRequest(int idRequest) {this.idRequest = idRequest;}

    public String getIdSender() {return idSender;}
    public void setIdSender(String idSender) {
        this.idSender = idSender;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getValidatorMessage() {return validatorMessage;}
    public void setValidatorMessage(String validatorMessage) {this.validatorMessage = validatorMessage;}

    public String getMotif() {return motif;}
    public void setMotif(String motif) {
        this.motif = motif;
        this.setStatus(Status.WAITING);
    }

    public Facilities getFacility() { return facility;}
    public void setFacility(Facilities facility) { this.facility = facility; }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {this.status = status;}

    public String getIdDestination() {
        return idDestination;
    }
    public void setIdDestination(String idDestination) {
        this.idDestination = idDestination;
    }

    // Static method to create a new request.Request instance
    public static Request createRequest(int idRequest, String idSender, String message, Facilities facility) {
        return new Request(idRequest, idSender, message, facility);
    }

    // Method to save or update the request in the database
    public void save() {
        try {
            boolean exists;
            try (PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                    .prepareStatement("SELECT 1 FROM request WHERE idRequest = ?")) {
                statement.setInt(1, idRequest);
                ResultSet resultSet = statement.executeQuery();
                exists = resultSet.next();
            }
            if (exists) {
                try (PreparedStatement updateStatement = DatabaseCreation.getInstance().getConnection()
                                .prepareStatement("UPDATE request SET idSender = ?, message = ?, validatorMessage = ?, motif = ?, facility = ?, status = ?, idDestination = ? WHERE idRequest = ?")) {
                    updateStatement.setString(1, idSender);
                    updateStatement.setString(2, message);
                    updateStatement.setString(3, validatorMessage);
                    updateStatement.setString(4, motif);
                    updateStatement.setString(5, facility.toString());
                    updateStatement.setString(6, status.toString());
                    updateStatement.setString(7, idDestination);
                    updateStatement.setInt(8, idRequest);
                    updateStatement.executeUpdate();
                }
            } else {
                try (PreparedStatement insertStatement = DatabaseCreation.getInstance().getConnection()
                        .prepareStatement("INSERT INTO request (idRequest, idSender, message, validatorMessage, motif, facility, status, idDestination) " +
                                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {
                    insertStatement.setInt(1, idRequest);
                    insertStatement.setString(2, idSender);
                    insertStatement.setString(3, message);
                    insertStatement.setString(4, validatorMessage);
                    insertStatement.setString(5, motif);
                    insertStatement.setString(6, facility.toString());
                    insertStatement.setString(7, status.toString());
                    insertStatement.setString(8, idDestination);
                    insertStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String toString() {
        return "numéro de la requete : " + getIdRequest() + "identifiant de l'envoyeur : " + getIdSender() + " A destination de : " + getIdDestination() + " Message : " + getMessage() + " request.Status : " + getStatus() + " Facility : " + getFacility();
    }
}