import java.io.ObjectInputStream;
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

    public static Validator createValidator(String id, String pswd, Facilities facility) throws SQLException {

        // Vérifier si l'identifiant existe déjà dans la base de données

        PreparedStatement checkStatement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("SELECT COUNT(*) FROM validator WHERE id = ?");
        checkStatement.setString(1, id);
        ResultSet resultSet = checkStatement.executeQuery();

        if (resultSet.next() && resultSet.getInt(1) > 0) {
            throw new SQLException("User" + id + "already exists");

        }

        User.createUser(id,pswd);

        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                    .prepareStatement("INSERT INTO validator (id, facility) VALUES (?, ?)");

            statement.setString(1, id);
            statement.setString(2, facility.toString());

            statement.execute();
        statement.close();

        return new Validator(id,pswd,facility);
    }

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
    // Ici on souhaite récuperer les requêtes lié à la facility du validator
    public List<Request> getRequests() throws SQLException {
        // Requête SQL pour récupérer les demandes dont le statut est "en attente"
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("SELECT * FROM request WHERE facility = ? AND status = ?");

        statement.setString(1,getFacility().toString()); // en fonction de la facility du validator
        statement.setString(2, (String.valueOf(Status.WAITING)));

        // Exécution de la requête
        ResultSet resultSet = statement.executeQuery();

        // Liste des requêtes à renvoyer
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

    public boolean validate() throws Exception {
        boolean valid = false;
        List<Request> Requests = getRequests();
        if (!Requests.isEmpty()) {
            for (Request r : Requests) {
                if (r.getStatus() == Status.VALIDATED) {
                    System.out.println("\nThe request : " + r.toString() + " has already been approved .");
                    continue;  // Passer à la requête suivante, sans la valider à nouveau
                }
                System.out.println("\n The request is : " + r.toString());
                Scanner scanner = new Scanner(System.in);
                System.out.println("\n Do you want to validate this request? y/n");
                String response = scanner.nextLine();
                if (response.equals("y")) {
                    r.setStatus(Status.VALIDATED);
                    valid = true;
                } else if (response.equals("n")) {
                    r.setStatus(Status.REJECTED);
                    Scanner scannerValidatorMessage = new Scanner(System.in);
                    System.out.println("Please explain the reason behind your validatorMessage : ");
                    String validatorMessage = scannerValidatorMessage.nextLine();
                    r.setValidatorMessage(validatorMessage);
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





