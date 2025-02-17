package com.dano.dano_book_social.service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.dano.dano_book_social.constents.EmailTemplateName;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendEmail(
        String to,
        String username,
        EmailTemplateName emailTemplate,
        String confirmationUrl,
        String activationCode,
        String subject
    ) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
            mimeMessage,
            MimeMessageHelper.MULTIPART_MODE_MIXED,
            StandardCharsets.UTF_8.name()
        );

        Map<String, Object> props = new HashMap<>();

        props.put("username", username);
        props.put("confirmationUrl", confirmationUrl);
        props.put("activation_code", activationCode);

        Context context = new Context();
        context.setVariables(props);

        helper.setFrom("contact@dano.com");
        helper.setTo(to);
        helper.setSubject(subject);

        String template = templateEngine.process(emailTemplate.name().toLowerCase(), context);

        helper.setText(template, true);

        mailSender.send(mimeMessage);

    }
    
}
