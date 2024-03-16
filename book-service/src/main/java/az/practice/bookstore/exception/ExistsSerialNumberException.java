package az.practice.bookstore.exception;

public class ExistsSerialNumberException extends RuntimeException {
    public ExistsSerialNumberException(String s) {
        super(s);
    }
}
