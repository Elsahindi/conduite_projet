import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Volunteer extends User{

    public Volunteer(String id, String pswd) {
        super(id,pswd);
    }


    public void login(String id, String pswd){

    };

    public static Volunteer createVolunteer(String id, String pswd) throws SQLException {

        User.createUser(id,pswd);

        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("INSERT INTO volunteer (id) VALUES (?)");

        statement.setString(1, id);

        statement.execute();

        return new Volunteer(id,pswd);

    }

}
