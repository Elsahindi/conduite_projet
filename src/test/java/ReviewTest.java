import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReviewTest {

    @Test
    public void createReview() {
        String idAuthor = "author";
        String title = "Test Review";
        String content = "This is a test of the reviewm creation method.";

        Review review = Review.createReview(1, idAuthor, title, content);

        assertNotNull(review);
        assertEquals(idAuthor, review.getidAuthor());
        assertEquals(title, review.getTitle());
        assertEquals(content, review.getContent());
    }
}