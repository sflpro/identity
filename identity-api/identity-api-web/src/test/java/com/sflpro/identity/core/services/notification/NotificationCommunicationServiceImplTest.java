package com.sflpro.identity.core.services.notification;

import com.sflpro.notifier.api.client.notification.email.EmailNotificationResourceClient;
import com.sflpro.notifier.api.model.common.result.ResultResponseModel;
import com.sflpro.notifier.api.model.email.EmailNotificationModel;
import com.sflpro.notifier.api.model.email.request.CreateEmailNotificationRequest;
import com.sflpro.notifier.api.model.email.response.CreateEmailNotificationResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NotificationCommunicationServiceImplTest {

    private static final String SENDER_EMAIL = "senderEmail@gmail.com";

    private static final String SUBJECT_PREFIX = "PREFIX";

    @Mock
    private EmailNotificationResourceClient emailNotificationResourceClient;

    @InjectMocks
    NotificationCommunicationServiceImpl notificationCommunicationService;

    @Before
    public void init() throws IllegalAccessException {
        FieldUtils.writeField(notificationCommunicationService, "senderEmail", SENDER_EMAIL, true);
        FieldUtils.writeField(notificationCommunicationService, "subjectPrefix", SUBJECT_PREFIX, true);
    }

    @Test
    public void sendSecretResetEmail() {
        final String email = random(5);
        final String emailTemplateName = random(5);
        final SecretResetNotificationRequest request = new SecretResetNotificationRequest(email, emailTemplateName, Collections.emptyMap());
        final Long notificationId = RandomUtils.nextLong();

        CreateEmailNotificationRequest emailNotificationRequest = new CreateEmailNotificationRequest();
        emailNotificationRequest.setRecipientEmail(request.getEmail());
        emailNotificationRequest.setSenderEmail(SENDER_EMAIL);
        emailNotificationRequest.setSubject(String.format("%s %s", SUBJECT_PREFIX, request.getEmailTemplateName()));
        emailNotificationRequest.setProperties(request.getEmailTemplateProperties());
        emailNotificationRequest.setTemplateName(request.getEmailTemplateName());

        EmailNotificationModel notification = new EmailNotificationModel();
        notification.setId(notificationId);
        final ResultResponseModel<CreateEmailNotificationResponse> responseModel = new ResultResponseModel<>(new CreateEmailNotificationResponse(notification));
        responseModel.setErrors(Collections.emptyList());
        when(emailNotificationResourceClient.createEmailNotification(eq(emailNotificationRequest))).thenReturn(responseModel);

        final Long id = notificationCommunicationService.sendSecretResetEmail(request);
        Assert.assertEquals(notificationId, id);

        Mockito.verify(emailNotificationResourceClient).createEmailNotification(eq(emailNotificationRequest));
    }

}