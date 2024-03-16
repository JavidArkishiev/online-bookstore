package az.practice.bookstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrdersNotFoundException extends RuntimeException {
    public OrdersNotFoundException(String s) {
        super(s);
    }
}
