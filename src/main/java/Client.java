import java.sql.*;

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

        //liste de demandes

    }

}
