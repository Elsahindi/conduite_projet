import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Client extends User{


    public Facilities facility;

    public Client(String id, String pswd, Facilities facility) {
        super(id,pswd);
        this.facility = facility ;
    }

    public static Client createClient(String id, String pswd, Facilities facility) throws SQLException {

        // Vérifier si l'identifiant existe déjà dans la base de données

        PreparedStatement checkStatement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("SELECT COUNT(*) FROM client WHERE id = ?");
        checkStatement.setString(1, id);
        ResultSet resultSet = checkStatement.executeQuery();

        if (resultSet.next() && resultSet.getInt(1) > 0) {
            throw new SQLException("User" + id + "already exists");

        }
        // Si l'identifiant n'existe pas, on continue avec la création du client

        User.createUser(id,pswd);

        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("INSERT INTO client (id,facility) VALUES (?,?)");

        statement.setString(1, id);
        statement.setString(2,facility.toString());

        statement.execute();

        return new Client(id,pswd,facility);

    }

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
    }

    public static void sendRequest(Request request) throws SQLException {

        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("INSERT INTO request (idSender,message,facility) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);


        statement.setString(1, request.getIdSender());
        statement.setString(2, request.getMessage());
        statement.setString(3, request.getFacility().toString());

        // Récuperer l'id de la requete

        statement.execute();

    }

    @Override
    public List<Request> getRequests() throws SQLException {

        // On cherche ici la liste des requêtes liées au Client
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("SELECT * FROM request WHERE request.idSender = ?");
        statement.setString(1, getId());
        ResultSet resultSet = statement.executeQuery();

        // On crée ici une liste qui contient les requêtes des clients
        List<Request> requests = new ArrayList<>();
        while (resultSet.next()) {
            requests.add(new Request(resultSet.getInt("idRequest"),resultSet.getString("idSender"),
                    resultSet.getString("message"),
                    Facilities.valueOf(resultSet.getString("facility").toUpperCase()),
                    Status.valueOf(resultSet.getString("status").toUpperCase()),
                    resultSet.getString("idDestination")));

            }
        return requests;
    }


}
