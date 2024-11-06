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
        User.createUser(id,pswd);

        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("INSERT INTO client (id,facility) VALUES (?,?)");

        statement.setString(1, id);
        statement.setString(2,facility.toString());

        statement.execute();

        return new Client(id,pswd,facility);

    }

    public Client getUser(String id) throws SQLException {

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


                return new Client(id,this.getPswd(),facility);
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

    public void login(String id, String pswd) {
        int connected = 0;
        try {
            if (getUser(id).getId().equals(id)) {
                connected = 1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void sendRequest(String id, String message) throws SQLException {

        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("INSERT INTO request (id,message) VALUES (?,?)");

        statement.setString(1, id);
        statement.setString(2,message);

        statement.execute();

    }

    @Override
    public List<Request> getRequests() throws SQLException {
        // On cherche ici la liste des requêtes liés au Client
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("SELECT * FROM request WHERE request.idSender = ?");
        statement.setString(1, getId());
        ResultSet resultSet = statement.executeQuery();

        // On crée ici une liste qui contient les requêtes des clients
        List<Request> requests = new ArrayList<>();
        while (resultSet.next()) {
            requests.add(new Request(resultSet.getString("idSender"),
                    resultSet.getString("message"),
                    Request.Status.valueOf(resultSet.getString("status").toUpperCase()),
                    resultSet.getString("idDestination")));
            }
        return requests;
    }


}
