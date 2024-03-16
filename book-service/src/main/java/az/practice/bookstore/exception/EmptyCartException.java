package az.practice.bookstore.exception;

public class EmptyCartException extends Throwable {
    public EmptyCartException(String s) {
        super(s);
    }
}
