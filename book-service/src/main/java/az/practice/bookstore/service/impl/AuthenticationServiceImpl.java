package az.practice.bookstore.service.impl;

import az.practice.bookstore.enums.Role;
import az.practice.bookstore.exception.*;
import az.practice.bookstore.model.dto.request.*;
import az.practice.bookstore.model.dto.response.JwtAuthenticationResponse;
import az.practice.bookstore.model.entity.Users;
import az.practice.bookstore.repository.UserRepository;
import az.practice.bookstore.service.AuthenticationService;
import az.practice.bookstore.service.EmailService;
import az.practice.bookstore.service.JWTService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final EmailService emailService;


    @Override
    public void signUpUser(SignUpRequest signUpRequest) throws MessagingException {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ExistsEmailException("this email already exist");
        }

        if (userRepository.existsByPhoneNumber(signUpRequest.getPhoneNumber())) {
            throw new ExistsPhoneNumberException("this phone number already used");
        }

        var user = Users.builder().firstName(signUpRequest.getFirstName()).lastName(signUpRequest.getLastName())
                .email(signUpRequest.getEmail())
                .phoneNumber(signUpRequest.getPhoneNumber())
                .createAt(LocalDateTime.now())
                .otpGeneratedTime(LocalDateTime.now())
                .role(Role.USER)
                .password(passwordEncoder.encode(signUpRequest.getPassword())).build();

        String otp = generateOtp();
        user.setOtp(otp);
        userRepository.save(user);
        sendVerificationEmail(user.getEmail(), otp);

    }

    @Override
    public void signUpAdmin(SignUpRequest signUpRequest) throws MessagingException {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ExistsEmailException("this email already exist");
        }
        if (userRepository.existsByPhoneNumber(signUpRequest.getPhoneNumber())) {
            throw new ExistsPhoneNumberException("this phone number already used");
        }

        var user = Users.builder().firstName(signUpRequest.getFirstName()).lastName(signUpRequest.getLastName())
                .email(signUpRequest.getEmail())
                .phoneNumber(signUpRequest.getPhoneNumber())
                .createAt(LocalDateTime.now())
                .otpGeneratedTime(LocalDateTime.now())
                .role(Role.ADMIN)
                .password(passwordEncoder.encode(signUpRequest.getPassword())).build();

        String otp = generateOtp();
        user.setOtp(otp);
        userRepository.save(user);
        sendVerificationEmail(user.getEmail(), otp);

    }

    public JwtAuthenticationResponse sigIn(SignInRequest sign) throws PasswordException {
        var user = userRepository.findByEmail(sign.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (user.getPassword() == null) {
            throw new PasswordException("you can not assign new password yet. Please assign new password");
        }
        if (!user.isEnabled()) {
            throw new AccountStatusException("your account is not active. Please first of all verify your account");

        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(sign.getEmail(), sign.getPassword()));
        } catch (AuthenticationException e) {
            throw new PasswordException("Invalid email or password");
        }

        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        LocalDateTime userOtpGeneratedTime = user.getOtpGeneratedTime();
        LocalDateTime now = LocalDateTime.now();
        long daysSinceDeactivation = ChronoUnit.DAYS.between(userOtpGeneratedTime, now);
        if (daysSinceDeactivation >= 7) {
            user.setEnabled(true);
            user.setOtpGeneratedTime(now);
            userRepository.save(user);
            return JwtAuthenticationResponse.builder().accessToken(jwt).refreshToken(refreshToken).build();
        }
        if (user.isEnabled()) {
            passwordEncoder.matches(sign.getPassword(), user.getPassword());
        }
        return JwtAuthenticationResponse.builder().accessToken(jwt).refreshToken(refreshToken).build();
    }

    public String generateOtp() {
        Random random = new Random();
        int otpValue = 100000 + random.nextInt(900000);
        return String.valueOf(otpValue);
    }

    private void sendVerificationEmail(String email, String otp) throws MessagingException {
        String subject = "Email verification";
        String body = "your verification otp is: " + otp;
        emailService.sendEmail(email, subject, body);
    }


    @Override
    public void verify(String email, String otp) throws OtpTimeException {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found :" + email));

        if (user.isEnabled()) {
            throw new VerifyError("User already verified");

        }
        if (!otp.equals(user.getOtp())) {
            throw new OtpTimeException("otp is not equals");

        }
        if (Duration.between(user.getOtpGeneratedTime()
                        , LocalDateTime.now()).

                getSeconds() < 2 * 60) {
            user.setEnabled(true);
            userRepository.save(user);

        } else throw new
                OtpTimeException("opt time is over.please regenerateOtp");


    }

    @Override
    public String regenerateOtp(String email) throws MessagingException {
        String otp = generateOtp();
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found :" + email));
        user.setOtp(otp);
        user.setOtpGeneratedTime(LocalDateTime.now());
        userRepository.save(user);
        sendVerificationEmail(email, otp);
        return "Email sent. Please verify account during 2 minute";
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest request) {
        String userEmail = jwtService.extractUsername(request.getToken());
        Users user = userRepository.findByEmail(userEmail).orElseThrow();
        if (jwtService.isTokenValid(request.getToken(), user)) {
            var jwt = jwtService.generateToken(user);

            return JwtAuthenticationResponse.builder().accessToken(jwt).refreshToken(request.getToken()).build();
        }
        return null;


    }

    @Override
    public void deActivateAccount(String email) throws MessagingException {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found :" + email));
        regenerateOtp(email);
        userRepository.save(user);

    }

    public void verifyForDeActivate(String email, String otp) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found :" + email));

        if (otp.equals(user.getOtp()) && Duration.between(user.getOtpGeneratedTime()
                        , LocalDateTime.now()).

                getSeconds() < (2 * 60)) {
            user.setEnabled(false);
            userRepository.save(user);
//        } else if (!email.equals(user.getEmail())) {
//            throw new RuntimeException("email not equals");

        } else throw new

                RuntimeException("opt time is over.please regenerateOtp");

    }


    @Override
    public void forgetPassword(ForgetPasswordRequest request) throws MessagingException {
        Users user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found :" + request.getEmail()));
        regenerateOtp(request.getEmail());
        user.setPassword(null);
        userRepository.save(user);

    }

    @Override
    public void resetPassword(String otp, String newPassword) {
        Users user = userRepository.findByOtp(otp)
                .orElseThrow(() -> new UserNotFoundException("User not found :" + otp));
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setOtp(otp);
        userRepository.save(user);

    }

    @Override
    public void changePassword(Principal principal, ChangePasswordRequest request) {
        var user = (Users) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new PasswordException("current password not true");
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new PasswordException("newPassword is not same with confirm password ");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

    }


}


