package az.practice.bookstore.exception;

public class ExistsUserException extends RuntimeException {
    public ExistsUserException(String s) {
        super(s);
    }
}
