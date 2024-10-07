import java.sql.Statement;

public class Client extends User{

    enum Facility {
        HOSPITAL,
        RETIREMENT,
        SCHOOL
    }

    private Facility facility;

    public Client(String id, String pswd, Facility facility) {
        super(id,pswd);
        this.facility = facility;
    }



    public void login(String id, String pswd, Facility facility){
        String selectSql = "SELECT * FROM client WHERE id =" + id + "AND pswd =" + pswd + "AND facility=" + facility;
        try (DatabaseCreation.ResultSet resultSet = stmt.executeQuery(selectSql)) {
            if (selectSql != NULL){
                System.out.println("User does exist");
            }
            else {
                System.out.println("User does not exist");
            }
        }
    };

}
