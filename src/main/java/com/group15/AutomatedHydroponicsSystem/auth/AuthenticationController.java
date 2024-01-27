package com.group15.AutomatedHydroponicsSystem.auth;

//import com.group15.AutomatedHydroponicsSystem.Mail.EmailSendController;
//import com.group15.AutomatedHydroponicsSystem.Mail.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
//@RequestMapping("/")
@RequiredArgsConstructor
public class AuthenticationController {
    //private final EmailService  emailService;
    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) throws UserAlreadyExistsException {
        //AuthenticationResponse registrationResponse = service.register(request);
        //sendWelcomeEmail(request);
        return ResponseEntity.ok(service.register(request));
    }

//    private void sendWelcomeEmail(RegisterRequest userRequest) {
//        String subject = "Welcome to Automated Hydroponics System";
//        String body = String.format(
//                "Thank you, %s %s, for registering with us! You have successfully logged in to the system. Your role is %s.",
//                userRequest.getFirstname(),
//                userRequest.getLastname(),
//                userRequest.getRole().name()
//        );
//
//        // Assuming you have the EmailService bean injected
//        emailService.sendMail(userRequest.getEmail(), subject, body);
//    }


    /*@PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) throws UserAlreadyExistsException {
        AuthenticationResponse registrationResponse = service.register(request);
        sendWelcomeEmail(request.getEmail());
        return ResponseEntity.ok(registrationResponse);
    }*/

    /*private void sendWelcomeEmail(String userEmail) {

        // Fetch additional user details or customize the email content if needed
        // For simplicity, assuming you have the email service bean available
        String subject = "Welcome to Automated Hydroponics System";
        String body = "Thank you for registering with us! You have successfully logged in to the system.";

        // Assuming you have the EmailService bean injected
        emailService.sendMail( userEmail, subject, body);
    }*/
    /*private void sendWelcomeEmail(RegisterRequest userRequest) {

        // Fetch additional user details or customize the email content if needed
        String firstName = userRequest.getFirstName();
        String lastName = userRequest.getLastName();
        String userEmail = userRequest.getEmail();
        String userRole = userRequest.getRole();

        String subject = "Welcome to Automated Hydroponics System";
        String body = String.format(
                "Dear %s %s,\n\n" +
                        "Thank you for registering with us! You have successfully logged in to the system.\n\n" +
                        "User Details:\n" +
                        "First Name: %s\n" +
                        "Last Name: %s\n" +
                        "Email: %s\n" +
                        "Role: %s",
                firstName, lastName, firstName, lastName, userEmail, userRole
        );

        // Assuming you have the EmailService bean injected
        emailService.sendMail(userEmail, subject, body);
    }*/


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody AuthenticationRequest request
    ){
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request,response);
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }


}
