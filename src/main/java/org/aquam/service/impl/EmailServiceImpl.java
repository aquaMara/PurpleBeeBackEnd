package org.aquam.service.impl;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.aquam.configuration.MailConfiguration;
import org.aquam.exception.EmailSendingException;
import org.aquam.model.request.ConfirmationRequest;
import org.aquam.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final MailConfiguration mailConfiguration;
    private static Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Override
    public String sendEmail(ConfirmationRequest confirmationRequest) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(createMessageBody(confirmationRequest), true);
            helper.setTo(confirmationRequest.getEmail());
            helper.setSubject("Confirmation email");
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            logger.error("Class: " + e.getClass() + " Message: " + e.getMessage() + " User email: " + confirmationRequest.getEmail());
            throw new EmailSendingException("Failed to send email, check email address");
        }

        return "email to address " + confirmationRequest.getEmail() + " was sent";
    }

    @Override
    public String createMessageBody(ConfirmationRequest confirmationRequest) throws TemplateException, IOException {
        Template template = mailConfiguration.factoryBean().createConfiguration().getTemplate("registration-confirmation.ftlh");
        Map<String, String> model = new HashMap<>();
        model.put("link", confirmationRequest.getConfirmationLink());
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
    }
}
