import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Validator extends User{

    public Facilities facility;

    public Validator(String id, String pswd, Facilities facility) {
        super(id,pswd);
        this.facility = facility;
    }


    public static Validator createValidator(String id, String pswd, Facilities facility) throws SQLException {
        User.createUser(id,pswd);

        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("INSERT INTO validator (id,facility) VALUES (?,?)");

        statement.setString(1, id);
        statement.setString(2,facility.toString());

        statement.execute();

        return new Validator(id,pswd,facility);
    }

    public Validator getUser(String id) throws SQLException {
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("SELECT * FROM user INNER JOIN validator ON user.id = validator.id WHERE id = ?");

        statement.setString(1, id);
        statement.execute();

        return new Validator(id,pswd,facility) ;

    }

    public void login(String id, String pswd){};

}
