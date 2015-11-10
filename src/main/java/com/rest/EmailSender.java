package com.rest;

import com.utils.BundleMessageReader;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

/**
 * Created by aautushk on 10/9/2015.
 */
public class EmailSender {
    public JavaMailSender mailSender;

    public ClassLoaderTemplateResolver emailTemplateResolver;

    public SpringTemplateEngine thymeleaf;

    String sender;

    String recipient;

    private BundleMessageReader bundleMessageReader = new BundleMessageReader();

    public EmailSender(JavaMailSender mailSenderParam,
                       ClassLoaderTemplateResolver emailTemplateResolverParam, SpringTemplateEngine thymeleafParam, String recipientParam, String senderParam){
        mailSender = mailSenderParam;
        emailTemplateResolver = emailTemplateResolverParam;
        thymeleaf = thymeleafParam;
        if(!thymeleaf.isInitialized()){
            thymeleaf.setTemplateResolver(emailTemplateResolver);
        }
        recipient = recipientParam;
        sender = senderParam;

    }

    public void sendVerificationTokenEmail(String emailVerificationToken, String requestBaseUrl){
        Locale locale = LocaleContextHolder.getLocale();
        String emailVerificationTemplate = "";
        switch (locale.toString()){
            case "fr":
                emailVerificationTemplate = "emailVerificationTemplate_fr";
                break;
            default:
                emailVerificationTemplate = "emailVerificationTemplate_en";
                break;
        }

        Context ctx = new Context();
        ctx.setVariable("emailVerificationToken", emailVerificationToken.toString());
        ctx.setVariable("requestBaseUrl", requestBaseUrl.toString());

        String emailText = thymeleaf.process(emailVerificationTemplate, ctx);

        try {
            sendMessage(bundleMessageReader.getMessage("EmailVerificationEmailHeader"), emailText);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void sendReserPasswordEmail(String resetPasswordToken, String requestBaseUrl){
        Locale locale = LocaleContextHolder.getLocale();
        String resetPasswordTemplate = "";
        switch (locale.toString()){
            case "fr":
                resetPasswordTemplate = "resetPasswordTemplate_fr";
                break;
            default:
                resetPasswordTemplate = "resetPasswordTemplate_en";
                break;
        }

        Context ctx = new Context();
        ctx.setVariable("resetPasswordToken", resetPasswordToken.toString());
        ctx.setVariable("requestBaseUrl", requestBaseUrl.toString());

        String emailText = thymeleaf.process(resetPasswordTemplate, ctx);

        try {
            sendMessage(bundleMessageReader.getMessage("ResetPasswordEmailHeader"), emailText);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String emailHeader, String emailText) throws MessagingException{
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(sender);
        helper.setTo(recipient);
        helper.setSubject(emailHeader);
        helper.setText(emailText, true /* isHtml */);
        mailSender.send(message);
    }
}
