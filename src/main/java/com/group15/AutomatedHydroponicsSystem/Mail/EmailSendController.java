//package com.group15.AutomatedHydroponicsSystem.Mail;
//
//import jakarta.mail.Multipart;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequestMapping("/api/v1/auth/mail")
//public class EmailSendController {
//
//    private EmailService emailService;
//
//    public EmailSendController(EmailService emailService) {
//        this.emailService = emailService;
//    }
//
//    @PostMapping("/send")
//    public String sendMail(@RequestParam (value = "file", required = false)MultipartFile[] file, String to, String subject, String body){
//        return emailService.sendMail( to,  subject, body);
//    }
//
//}
