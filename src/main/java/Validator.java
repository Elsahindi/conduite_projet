import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Validator extends User{

    public Facilities facility;

    public Validator(String id, String pswd, Facilities facility) {
        super(id,pswd);
        this.facility = facility;
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

    public Validator getUser(String id) throws SQLException {
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

    public void login(String id, String pswd) {
        int connected = 0;
        try {
            if (getUser(id).getId().equals(id)){
                connected = 1;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    };

    public Facilities getFacility() {
        return facility;
    }

    @Override
    // Ici on souhaite récuperer les requêtes lié à la facility du validator
    public List<Request> getRequests() throws SQLException {
        return null;
    }

}



