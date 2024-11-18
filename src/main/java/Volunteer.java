import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Volunteer extends User{

    public Volunteer(String id, String pswd) {
        super(id,pswd);
    }

    public static Volunteer createVolunteer(String id, String pswd) throws SQLException {

        // Vérifier si l'identifiant existe déjà dans la base de données

        PreparedStatement checkStatement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("SELECT COUNT(*) FROM volunteer WHERE id = ?");
        checkStatement.setString(1, id);
        ResultSet resultSet = checkStatement.executeQuery();

        if (resultSet.next() && resultSet.getInt(1) > 0) {
            throw new SQLException("User" + id + "already exists");

        }
        User.createUser(id,pswd);

        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("INSERT INTO volunteer (id) VALUES (?)");

        statement.setString(1, id);

        statement.execute();

        return new Volunteer(id,pswd);

    }

    public static Volunteer getUser(String id) throws SQLException {

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = DatabaseCreation.getInstance().getConnection()
                    .prepareStatement("SELECT * FROM user INNER JOIN volunteer ON user.id = volunteer.id WHERE user.id = ?");

            statement.setString(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                String pswd = resultSet.getString("pswd");

                return new Volunteer(id,pswd);
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

    public int login(String id, String pswd){

        int connected = 0;
        try {
            if (getUser(id).getId().equals(id) && getUser(id).getPswd().equals(pswd)){
                connected = 1;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connected;
    };


    public static List<Request> seeAllRequests() throws SQLException {

        // Requête SQL pour récupérer les demandes dont le statut est "en attente"
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("SELECT * FROM request WHERE status = ?");

        statement.setString(1, Status.VALIDATED.name()); // Statut "validée" par le validator

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
    @Override
    // Récupère les requête dont l'iD destination est l'iD du volunteer
    public List<Request> getRequests() throws SQLException {
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("SELECT * FROM request WHERE idDestination = ? AND NOT status = ?");
        statement.setString(1, getId());
        statement.setString(2, Status.DONE.name());
        ResultSet resultSet = statement.executeQuery();

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

    //Permet au bénévole de choisir la requete qu'il souhaite accomplir
    public void chooseRequest(){
        try {
            List<Request> liste = seeAllRequests();
            for (Request request : liste) {
                System.out.println("\n The request is : " + request.toString());
                Scanner scanner = new Scanner(System.in);
                System.out.println("\n Do you volunteer to handle this task? y/n");
                String response = scanner.nextLine();
                if (response.equals("y")) {
                    request.setIdDestination(getId());
                    request.setStatus(Status.ACCEPTED);
                    request.save();
                } else if (response.equals("n")) {
                    request.setStatus(Status.VALIDATED);
                    request.save();
                    System.out.println("status is: " + request.getStatus());
                } else {
                    System.out.println("The response should be y or n");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}


