import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

}


