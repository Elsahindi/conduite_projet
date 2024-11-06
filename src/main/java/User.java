import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

abstract class User {

    private String id;
    private String pswd;

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

    public abstract User getUser(String id) throws SQLException;

    public abstract void login(String id, String pswd);

    public abstract List<Request> getRequests() throws SQLException;
}
