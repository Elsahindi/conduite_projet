import java.sql.PreparedStatement;
import java.sql.SQLException;

abstract class User {

    public String id;
    public String pswd;

    public User(String id, String pswd) {
        this.id = id;
        this.pswd = pswd;
    }

    public String getId() {
        return id;
    }

    public String getPswd() {
        return pswd;
    }

    public static void createUser(String id, String pswd) throws SQLException {

        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("INSERT INTO user (id, pswd) VALUES (?,?)");

        statement.setString(1, id);
        statement.setString(2, pswd);

        statement.execute();
    }

    public abstract void login(String id, String pswd);
}
