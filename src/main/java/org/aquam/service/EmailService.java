package org.aquam.service;

import freemarker.template.TemplateException;
import org.aquam.model.request.ConfirmationRequest;
import org.springframework.stereotype.Repository;

import java.io.IOException;

@Repository
public interface EmailService {

    String sendEmail(ConfirmationRequest confirmationRequest);
    String createMessageBody(ConfirmationRequest confirmationRequest) throws TemplateException, IOException;
    Boolean sendSupportLetter(String email, String body);
}
