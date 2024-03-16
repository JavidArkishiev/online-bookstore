package az.practice.bookstore.exception;

public class StockLimitException extends Throwable {
    public StockLimitException(String s) {
        super(s);
    }
}
