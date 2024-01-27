//package com.group15.AutomatedHydroponicsSystem.Mail.Impl;
//
//import com.group15.AutomatedHydroponicsSystem.Mail.EmailService;
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailServiceImpl implements EmailService {
//
//    @Value("${spring.mail.username}")
//    private final String fromEmail;
//
//    private final JavaMailSender javaMailSender;
//
//
//
//    public EmailServiceImpl(JavaMailSender javaMailSender, @Value("${spring.mail.username}") String fromEmail) {
//        this.javaMailSender = javaMailSender;
//        this.fromEmail = fromEmail;
//    }
///*
//    @Override
//    public String sendMail(MultipartFile[] file, String to, String[] cc, String subject, String body) {
//        try {
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//
//            mimeMessageHelper.setFrom(fromEmail);
//            mimeMessageHelper.setTo(to);
//            mimeMessageHelper.setCc(cc);
//            mimeMessageHelper.setSubject(subject);
//            mimeMessageHelper.setText(body);
//
//            for (MultipartFile multipartFile : file) {
//                mimeMessageHelper.addAttachment(
//                        Objects.requireNonNull(multipartFile.getOriginalFilename()),
//                        new ByteArrayResource(multipartFile.getBytes()));
//            }
//
//            javaMailSender.send(mimeMessage);
//
//            return "mail send";
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }*/
//    public String sendMail(String to, String subject, String text) {
//
//        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//        try {
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//            mimeMessageHelper.setTo(to);
//            mimeMessageHelper.setSubject(subject);
//            mimeMessageHelper.setText(text, true);
//
//            /*// Check if ccAddresses is not null before setting cc
//            // Note: If you don't want to set cc at all, you can skip this block
//            String[] ccAddresses = // your cc addresses ;
//            if (ccAddresses != null) {
//                mimeMessageHelper.setCc(ccAddresses);
//            }*/
//
//            javaMailSender.send(mimeMessage);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//            // Handle exception
//        }
//        return "mail send";
//    }
//
//
//
//    @Override
//    public String sendWelcomeEmail(String to) {
//        String subject = "Welcome to Automated Hydroponics System";
//        String body = "Thank you for registering with us! You have successfully logged in to the system.";
//
//        try {
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
//
//            mimeMessageHelper.setFrom(fromEmail);
//            mimeMessageHelper.setTo(to);
//            mimeMessageHelper.setSubject(subject);
//            mimeMessageHelper.setText(body);
//
//            javaMailSender.send(mimeMessage);
//
//            return "Welcome email sent";
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//}
