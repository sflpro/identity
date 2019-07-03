package com.sflpro.identity.core.services.notification;

import com.sflpro.identity.core.db.entities.Token;
import com.sflpro.identity.core.services.identity.IdentityServiceException;
import com.sflpro.identity.core.services.identity.reset.RequestSecretResetRequest;
import com.sflpro.identity.core.services.principal.PrincipalService;
import com.sflpro.notifier.api.client.notification.email.EmailNotificationResourceClient;
import com.sflpro.notifier.api.model.common.result.ResultResponseModel;
import com.sflpro.notifier.api.model.email.request.CreateEmailNotificationRequest;
import com.sflpro.notifier.api.model.email.response.CreateEmailNotificationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * Company: SFL LLC
 * Created on 08/01/2018
 *
 * @author Davit Harutyunyan
 */
@Service
public class NotificationCommunicationServiceImpl implements NotificationCommunicationService {

    @Autowired
    private EmailNotificationResourceClient emailNotificationResourceClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendSecretResetEmail(@Valid RequestSecretResetRequest request, @NotNull Token token) {
        Assert.notNull(request.getEmail(), "email cannot be null");
        Assert.notNull(token, "secret reset token cannot be null");
        Assert.notNull(token.getValue(), "secret reset token value cannot be null");

        Map<String, String> propertiesMap = new HashMap<>();
        propertiesMap.put("RESET_TOKEN", token.getValue());
        propertiesMap.put("REDIRECT_URI", request.getRedirectUri());
        if (request.getEmailTemplateProperties() != null) {
            propertiesMap.putAll(request.getEmailTemplateProperties());
        }


        CreateEmailNotificationRequest emailNotificationRequest = new CreateEmailNotificationRequest();
        emailNotificationRequest.setRecipientEmail(request.getEmail());
        emailNotificationRequest.setSenderEmail("sender@weadapt.digital");
        emailNotificationRequest.setSubject("Weadapt Sign Up");
        emailNotificationRequest.setProperties(propertiesMap);
        emailNotificationRequest.setTemplateName(request.getEmailTemplateName());


        ResultResponseModel<CreateEmailNotificationResponse> emailNotificationResponse = emailNotificationResourceClient.createEmailNotification(emailNotificationRequest);
        if (emailNotificationResponse.hasErrors()) {
            throw new IdentityServiceException("Mail was not sent");
        }
    }
}
