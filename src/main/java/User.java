import java.sql.PreparedStatement;

abstract class User {

    public int id;
    public String pswd;

    public User(int id, String pswd) {
        this.id = id;
        this.pswd = pswd;
    }

    public void create_user(){
        PreparedStatement statement = DatabaseCreation.getInstance().getConnector()
                .


    }

    public abstract void login();

}
