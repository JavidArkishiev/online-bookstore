package az.practice.bookstore.exception;

public class ReviewNotFound extends RuntimeException {
    public ReviewNotFound(String s) {
        super(s);
    }
}
