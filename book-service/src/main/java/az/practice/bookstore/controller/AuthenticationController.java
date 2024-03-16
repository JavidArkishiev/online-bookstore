package az.practice.bookstore.controller;

import az.practice.bookstore.exception.OtpTimeException;
import az.practice.bookstore.exception.PasswordException;
import az.practice.bookstore.model.dto.request.*;
import az.practice.bookstore.model.dto.response.JwtAuthenticationResponse;
import az.practice.bookstore.service.AuthenticationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("adminSignUp")
    public ResponseEntity<String> signUpAdmin(@RequestBody @Valid SignUpRequest signUpRequest) throws MessagingException {
        authenticationService.signUpAdmin(signUpRequest);
        return ResponseEntity.ok("Registration successful." +
                "Please verify your account for to do activate during 2 minutes.Check out your gmail.");
    }

    @PostMapping("userSignUp")
    public ResponseEntity<String> signUpUser(@RequestBody @Valid SignUpRequest signUpRequest) throws MessagingException {
        authenticationService.signUpUser(signUpRequest);
        return ResponseEntity.ok("Registration successful." +
                "Please verify your account for to do activate during 2 minutes.Check out your gmail.");
    }

    @PostMapping("/signIn")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody @Valid SignInRequest sign) throws PasswordException {
        return ResponseEntity.ok(authenticationService.sigIn(sign));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody @Valid RefreshTokenRequest request) {
        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }


    @PostMapping("verify")
    public ResponseEntity<String> verifyUser(@RequestParam String email, String otp) throws OtpTimeException {
        authenticationService.verify(email, otp);
        return ResponseEntity.ok("Success.Your account has activated. You can login a website");

    }

    @PutMapping("regenerateOtp")
    public ResponseEntity<String> resetOtp(@RequestParam String email) throws MessagingException {
        return new ResponseEntity<>(authenticationService.regenerateOtp(email), HttpStatus.OK);

    }

    @PostMapping("/forgetPassword")
    public ResponseEntity<String> forgetPassword(@RequestBody ForgetPasswordRequest request) throws MessagingException {
        authenticationService.forgetPassword(request);
        return ResponseEntity.ok("OTP code sent to email address. You can reset password. Note OTP timeLimit is 2 minute");
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> deActivateAccount(@RequestParam String otp, @RequestParam String newPassword) {
        authenticationService.resetPassword(otp, newPassword);
        return ResponseEntity.ok("Password has been reset successfully. You can login a website with new password");
    }


    @PostMapping("changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request,
                                                 Principal principal) {
        authenticationService.changePassword(principal, request);
        return ResponseEntity.ok("Success");

    }

    @PostMapping("/deActiveAccount")
    public ResponseEntity<String> deActivateAccount(@RequestParam String email) throws MessagingException {
        authenticationService.deActivateAccount(email);
        return ResponseEntity.ok("OTP code sent to email address. Please deactivate your account. Note OTP timeLimit is 2 minute ");
    }

    @PostMapping("/verifyForDeActivate")
    public ResponseEntity<String> verifyForDeActivate(@RequestParam String email, String otp) throws MessagingException {
        authenticationService.verifyForDeActivate(email, otp);
        return ResponseEntity.ok("Success. Your account already has deactivated. See you soon ) you cant access your account during one week ");
    }

}
