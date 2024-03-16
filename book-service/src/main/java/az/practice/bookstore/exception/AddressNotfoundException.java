package az.practice.bookstore.exception;

public class AddressNotfoundException extends RuntimeException {
    public AddressNotfoundException(String s) {
        super(s);
    }
}
