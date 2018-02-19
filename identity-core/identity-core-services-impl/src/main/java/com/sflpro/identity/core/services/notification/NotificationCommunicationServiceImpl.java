package com.sflpro.identity.core.services.notification;

import com.sflpro.identity.core.datatypes.PrincipalType;
import com.sflpro.identity.core.db.entities.Principal;
import com.sflpro.identity.core.db.entities.Token;
import com.sflpro.identity.core.services.identity.IdentityServiceException;
import com.sflpro.identity.core.services.principal.PrincipalService;
import com.sflpro.notifier.api.internal.client.rest.notification.email.template.ApplicationNotificationProcessorResourceClient;
import com.sflpro.notifier.core.api.internal.model.common.result.ResultResponseModel;
import com.sflpro.notifier.core.api.internal.model.email.response.CreateEmailNotificationResponse;
import com.sflpro.notifier.core.api.internal.model.email.template.forgotpassword.ResetPasswordEmailClientModel;
import com.sflpro.notifier.core.api.internal.model.email.template.request.ForgotPasswordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;

/**
 * Company: SFL LLC
 * Created on 08/01/2018
 *
 * @author Davit Harutyunyan
 */
@Service
public class NotificationCommunicationServiceImpl implements NotificationCommunicationService {

    @Autowired
    private PrincipalService principalService;

    private ApplicationNotificationProcessorResourceClient applicationNotificationProcessorResourceClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendSecretResetEmail(@NotNull final String email, @NotNull final Token token) {
        Assert.notNull(email, "email cannot be null");
        Assert.notNull(token, "secret reset token cannot be null");
        Assert.notNull(token.getValue(), "secret reset token value cannot be null");

        ForgotPasswordRequest forgotPasswordRequest = new ForgotPasswordRequest();
        forgotPasswordRequest.setSenderEmail("sender@weadapt.digital");
        forgotPasswordRequest.setRecipientEmail(email);
        //forgotPasswordRequest.setRecipientEmail("davit.harutyunyan@sflpro.com");
        ResetPasswordEmailClientModel templateModel = new ResetPasswordEmailClientModel();
        // templateModel.setToken(token.getValue());
        templateModel.setVerificationToken(token.getValue());
        Principal principal = principalService.get(PrincipalType.MAIL, email);
        templateModel.setEmail(principal.getName());
        templateModel.setName(token.getIdentity().getDescription());
        forgotPasswordRequest.setTemplateModel(templateModel);
        ResultResponseModel<CreateEmailNotificationResponse> response = applicationNotificationProcessorResourceClient.processForgotPassword(forgotPasswordRequest);
        if (response.hasErrors()) {
            throw new IdentityServiceException("Mail was not sent");
        } // TODO think something here too
    }

    public void setApplicationNotificationProcessorResourceClient(final ApplicationNotificationProcessorResourceClient applicationNotificationProcessorResourceClient) {
        this.applicationNotificationProcessorResourceClient = applicationNotificationProcessorResourceClient;
    }
}
