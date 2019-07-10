package com.sflpro.identity.core.services.notification;

import com.sflpro.identity.core.services.identity.IdentityServiceException;
import com.sflpro.notifier.api.client.notification.email.EmailNotificationResourceClient;
import com.sflpro.notifier.api.model.common.result.ResultResponseModel;
import com.sflpro.notifier.api.model.email.request.CreateEmailNotificationRequest;
import com.sflpro.notifier.api.model.email.response.CreateEmailNotificationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.Valid;

/**
 * Company: SFL LLC
 * Created on 08/01/2018
 *
 * @author Davit Harutyunyan
 */
@Service
public class NotificationCommunicationServiceImpl implements NotificationCommunicationService {

    @Value("${sender.email}")
    private String senderEmail;

    @Value("${email.subject.prefix}")
    private String subjectPrefix;

    @Autowired
    private EmailNotificationResourceClient emailNotificationResourceClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendSecretResetEmail(@Valid SecretResetNotificationRequest notificationRequest) {
        Assert.notNull(notificationRequest.getEmail(), "email cannot be null");

        CreateEmailNotificationRequest emailNotificationRequest = new CreateEmailNotificationRequest();
        emailNotificationRequest.setRecipientEmail(notificationRequest.getEmail());
        emailNotificationRequest.setSenderEmail(senderEmail);
        emailNotificationRequest.setSubject(String.format("%s %s", subjectPrefix, notificationRequest.getEmailTemplateName()));
        emailNotificationRequest.setProperties(notificationRequest.getEmailTemplateProperties());
        emailNotificationRequest.setTemplateName(notificationRequest.getEmailTemplateName());


        ResultResponseModel<CreateEmailNotificationResponse> emailNotificationResponse = emailNotificationResourceClient.createEmailNotification(emailNotificationRequest);
        if (emailNotificationResponse.hasErrors()) {
            throw new IdentityServiceException("Mail was not sent");
        }
    }
}