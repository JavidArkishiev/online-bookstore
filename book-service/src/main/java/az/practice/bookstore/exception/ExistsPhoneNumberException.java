package az.practice.bookstore.exception;

public class ExistsPhoneNumberException extends RuntimeException {
    public ExistsPhoneNumberException(String s) {
        super(s);
    }
}
