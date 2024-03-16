package az.practice.bookstore.exception;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ExistsEmailException extends RuntimeException {

    public ExistsEmailException(@NotBlank(message = "email can not be null") @Pattern(regexp = "[\\w.-]+@[\\w.-]+.\\w+$") String s) {
        super(s);
    }
}
