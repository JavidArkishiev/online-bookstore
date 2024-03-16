package az.practice.bookstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;

@ControllerAdvice
public class ExceptionHande {

    @ExceptionHandler(BookNotFoundException.class)
//    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleBookNotFoundException(BookNotFoundException bookNotFoundException, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(bookNotFoundException.getMessage(), LocalDate.now(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationsException(MethodArgumentNotValidException exception) {
        ErrorDetails errorDetails = new ErrorDetails("Validation Exception", LocalDate.now(), exception.getBindingResult().getFieldError().getDefaultMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException userNotFoundException, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(userNotFoundException.getMessage(), LocalDate.now(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrdersNotFoundException.class)
    public ResponseEntity<?> handleOrdersNotFoundException(OrdersNotFoundException ordersNotFoundException, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(ordersNotFoundException.getMessage(), LocalDate.now(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExistsEmailException.class)
    public ResponseEntity<?> handleExistsEmailException(ExistsEmailException emailException, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(emailException.getMessage(), LocalDate.now(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExistsPhoneNumberException.class)
    public ResponseEntity<?> handleExistsPhoneNumberException(ExistsPhoneNumberException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(), LocalDate.now(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExistsSerialNumberException.class)
    public ResponseEntity<?> handleExistsPhoneNumberException(ExistsSerialNumberException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(), LocalDate.now(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StockLimitException.class)
    public ResponseEntity<?> handleStockLimitException(StockLimitException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(), LocalDate.now(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<?> handleCartNotFoundException(CartNotFoundException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(), LocalDate.now(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyCartException.class)
    public ResponseEntity<?> handleCEmptyCartException(EmptyCartException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(), LocalDate.now(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReviewNotFound.class)
    public ResponseEntity<?> handleReviewNotFound(ReviewNotFound exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(), LocalDate.now(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PasswordException.class)
    public ResponseEntity<?> handlePasswordException(PasswordException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(), LocalDate.now(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccountStatusException.class)
    public ResponseEntity<?> handleAccountStatusException(AccountStatusException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(), LocalDate.now(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OtpTimeException.class)
    public ResponseEntity<?> handleOtpTimeException(OtpTimeException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(), LocalDate.now(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VerifyError.class)
    public ResponseEntity<?> handleOtpVerifyError(VerifyError exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(), LocalDate.now(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AddressNotfoundException.class)
    public ResponseEntity<?> handleAddressNotfoundException(AddressNotfoundException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(), LocalDate.now(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExistsUserException.class)
    public ResponseEntity<?> handleExistsUserException(ExistsUserException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(exception.getMessage(), LocalDate.now(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
}
