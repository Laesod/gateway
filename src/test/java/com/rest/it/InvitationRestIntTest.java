package com.rest.it;

import com.MockGatewayApplication;
import com.dto.InvitationResponseDto;
import com.entity.InvitationEntity;
import com.repository.IInvitationRepository;
import com.repository.ITranslationRepository;
import com.rest.InvitationRest;
import com.utils.SecurityContextReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by aautushk on 9/26/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockGatewayApplication.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback=true)
@ActiveProfiles("test")
public class InvitationRestIntTest {
    @Autowired
    IInvitationRepository invitationRepository;

    @Autowired
    ITranslationRepository translationRepository;

    //user story 2.
    @Test
    public void getReceivedInvitations() {
        SecurityContextReader securityContextReaderMock = mock(SecurityContextReader.class);
        when(securityContextReaderMock.getUsername()).thenReturn("test@gmail.com");

        InvitationEntity invitationEntity = new InvitationEntity();
        invitationEntity.setRecipientEmail("test@gmail.com");
        invitationEntity.setAuthority("123_write");
        invitationEntity.setIsInvitationAccepted(false);
        invitationEntity.setProjectGuid("123");

        invitationRepository.save(invitationEntity);

        InvitationRest invitationRest = new InvitationRest();
        List<InvitationResponseDto> invitations = new ArrayList<InvitationResponseDto>();

        invitationRest.securityContextReader = securityContextReaderMock;
        invitationRest.invitationRepository = invitationRepository;
        invitationRest.translationRepository = translationRepository;

        invitations = invitationRest.getReceivedInvitations();

        assertEquals(invitations.size(), 1);
    }
}
