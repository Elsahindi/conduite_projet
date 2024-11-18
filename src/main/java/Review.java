import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Review {

    private int idReview;
    private String idAuthor;
    private String title;
    private String content;

    public Review(int idReview, String idAuthor, String title, String content) {
        this.idReview = idReview;
        this.idAuthor = idAuthor;
        this.title = title;
        this.content = content;
    }

    public int getIdReview() { return idReview; }

    public void setIdReview(int idReview) { this.idReview = idReview; }

    public String getidAuthor(){
        return idAuthor;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setAuthor(String idAuthor) {
        this.idAuthor = idAuthor;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static Review createReview(int idReview, String idAuthor, String title, String content) {
        return new Review(idReview, idAuthor, title, content);
    }

    public String toString() {
        return idAuthor + "\t" + title + "\t" + content;
    }

    public String getIdAuthor() {
        return idAuthor;
    }

    public void save(){
        PreparedStatement statement = null;
        try {
            statement = DatabaseCreation.getInstance().getConnection()
                    .prepareStatement("UPDATE review SET idAuthor = ?, title = ?, content WHERE idReview = ?");
            statement.setString(1,idAuthor);
            statement.setString(2,title);
            statement.setString(3,content);
            statement.setInt(4,idReview);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}


