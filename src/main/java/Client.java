import java.sql.*;

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
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("SELECT * FROM user INNER JOIN client ON user.id = client.id WHERE user.id = ?");

        statement.setString(1, id);
        statement.execute();

        return new Client(id,this.getPswd(),facility);

    }

    public void login(String id, String pswd) {
    }


}
