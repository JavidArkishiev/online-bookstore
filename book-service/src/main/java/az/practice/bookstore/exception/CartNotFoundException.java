package az.practice.bookstore.exception;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(String s) {
        super(s);
    }
}
