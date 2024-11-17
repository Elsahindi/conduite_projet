public class Review {

    private String IdAuthor;
    private String title;
    private String content;

    public Review(String IdAuthor, String title, String content) {
        this.IdAuthor = IdAuthor;
        this.title = title;
        this.content = content;
    }

    public String getIdAuthor(){
        return IdAuthor;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setAuthor(String IdAuthor) {
        this.IdAuthor = IdAuthor;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static Review createReview(String IdAuthor, String title, String content) {
        return new Review(IdAuthor, title, content);
    }

    public String toString() {
        return IdAuthor + "\t" + title + "\t" + content;
    }
}
