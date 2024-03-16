package az.practice.bookstore.service;

import az.practice.bookstore.exception.OtpTimeException;
import az.practice.bookstore.exception.PasswordException;
import az.practice.bookstore.model.dto.request.*;
import az.practice.bookstore.model.dto.response.JwtAuthenticationResponse;
import jakarta.mail.MessagingException;

import java.security.Principal;

public interface AuthenticationService {
    void signUpAdmin(SignUpRequest signUpRequest) throws MessagingException;
    void signUpUser(SignUpRequest signUpRequest) throws MessagingException;

    JwtAuthenticationResponse sigIn(SignInRequest sign) throws PasswordException;

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest request);

    void changePassword(Principal principal, ChangePasswordRequest request);

    void verify(String email, String otp) throws OtpTimeException;


    String regenerateOtp(String email) throws MessagingException;

    void forgetPassword(ForgetPasswordRequest request) throws MessagingException;

    void resetPassword(String otp, String newPassword);


    void deActivateAccount(String email) throws MessagingException;

    void verifyForDeActivate(String email, String otp);
}

