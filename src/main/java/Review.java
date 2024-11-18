public class Review {

    private String idAuthor;
    private String title;
    private String content;

    public Review(String idAuthor, String title, String content) {
        this.idAuthor = idAuthor;
        this.title = title;
        this.content = content;
    }

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

    public static Review createReview(String idAuthor, String title, String content) {
        return new Review(idAuthor, title, content);
    }

    public String toString() {
        return idAuthor + "\t" + title + "\t" + content;
    }

    public String getIdAuthor() {
        return idAuthor;
    }
}
