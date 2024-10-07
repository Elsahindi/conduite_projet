import java.sql.PreparedStatement;
import java.sql.SQLException;

abstract class User {

    public int id;
    public String pswd;

    public User(int id, String pswd) {
        this.id = id;
        this.pswd = pswd;
    }

    public int getId() {
        return id;
    }

    public String getPswd() {
        return pswd;
    }

    public void create_user() throws SQLException {
        PreparedStatement statement = DatabaseCreation.getInstance().getConnection()
                .prepareStatement();


    }

    public abstract void login();

}
