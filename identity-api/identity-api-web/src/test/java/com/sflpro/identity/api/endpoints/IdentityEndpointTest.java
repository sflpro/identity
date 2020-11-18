package com.sflpro.identity.api.endpoints;

import com.sflpro.identity.api.common.dtos.identity.reset.RequestSecretResetRequestDto;
import com.sflpro.identity.api.common.dtos.identity.reset.SecretResetResponseDto;
import com.sflpro.identity.api.mapper.BeanMapper;
import com.sflpro.identity.core.services.identity.IdentityService;
import com.sflpro.identity.core.services.identity.SecretResetResponse;
import com.sflpro.identity.core.services.identity.reset.RequestSecretResetRequest;
import com.sflpro.identity.core.services.resource.ResourceService;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class IdentityEndpointTest {

    @Mock
    private BeanMapper mapper;

    @Mock
    private IdentityService identityService;

    @Mock
    private ResourceService resourceService;

    @InjectMocks
    private IdentityEndpoint identityEndpoint;

    @Test
    public void requestSecretReset() {
        final RequestSecretResetRequestDto requestDto = new RequestSecretResetRequestDto();
        final RequestSecretResetRequest resetRequest = new RequestSecretResetRequest();
        final Long notificationId = RandomUtils.nextLong();
        final SecretResetResponse response = new SecretResetResponse(notificationId);

        Mockito.when(identityService.requestSecretReset(resetRequest)).thenReturn(response);
        Mockito.when(mapper.map(requestDto, RequestSecretResetRequest.class)).thenReturn(resetRequest);

        final SecretResetResponseDto responseDto = identityEndpoint.requestSecretReset(requestDto);
        Assert.assertEquals(notificationId, responseDto.getNotificationId());

        Mockito.verify(identityService).requestSecretReset(resetRequest);
        Mockito.verify(mapper).map(requestDto, RequestSecretResetRequest.class);
    }
}