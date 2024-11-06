import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Volunteer extends User{

    public Volunteer(String id, String pswd) {
        super(id,pswd);
    }

    public static Volunteer createVolunteer(String id, String pswd) throws SQLException {

        User.createUser(id,pswd);

        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("INSERT INTO volunteer (id) VALUES (?)");

        statement.setString(1, id);

        statement.execute();

        return new Volunteer(id,pswd);

    }

    public Volunteer getUser(String id) throws SQLException {

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = DatabaseCreation.getInstance().getConnection()
                    .prepareStatement("SELECT * FROM user INNER JOIN volunteer ON user.id = volunteer.id WHERE user.id = ?");

            statement.setString(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {

                String pswd = resultSet.getString("pswd");

                return new Volunteer(id,this.getPswd());
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

    public void login(String id, String pswd){

        int connected = 0;
        try {
            if (getUser(id).getId().equals(id)){
                connected = 1;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    @Override
    // Récupère les requête dont l'iD destination est l'iD du volunteer
    public List<Request> getRequests() throws SQLException {
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("SELECT * FROM request WHERE idDestination = ?");
        statement.setString(1, getId());
        ResultSet resultSet = statement.executeQuery();

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


