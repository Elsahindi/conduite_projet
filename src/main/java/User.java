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

    public void create_user(String id, String pswd) throws SQLException {
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement("INSERT INTO users (id, pswd)");

    }

    public abstract void login(String id, String pswd);
}
