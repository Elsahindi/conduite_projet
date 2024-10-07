import java.sql.*;

public class Client extends User{

    enum Facility {
        HOSPITAL,
        RETIREMENT,
        SCHOOL
    }

    private Facility facility;

    public Client(String id, String pswd, Facility facility) {
        super(id,pswd);
        this.facility = facility ;
    }

    public void login(String id, String pswd) {
        String selectSql = "SELECT * FROM client WHERE id = ? AND pswd = ? AND facility = ?";
        try (PreparedStatement pstmt = DatabaseCreation.getInstance().getConnection().prepareStatement(selectSql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, pswd);
            pstmt.setString(3, facility.toString());

            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.first()) {
                    System.out.println("User does exist");
                } else {
                    System.out.println("User does not exist");
                }
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while trying to log in.");
        }
    }


}
