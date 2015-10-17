package com.rest.unit;

import com.dto.InvitationResponseDto;
import com.repository.IInvitationRepository;
import com.rest.InvitationRest;
import com.utils.SecurityContextReader;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * Created by aautushk on 10/9/2015.
 */
public class InvitationRestUnitTest {
    InvitationRest invitationRest = new InvitationRest();
    SecurityContextReader mockSecurityContextReader = Mockito.mock(SecurityContextReader.class);
    IInvitationRepository mockInvitationRepository = Mockito.mock(IInvitationRepository.class);

    public InvitationRestUnitTest(){
        invitationRest.securityContextReader = mockSecurityContextReader;
        invitationRest.invitationRepository = mockInvitationRepository;
    }

    //user story 2.
    @Test
    public void getReceivedInvitations(){
        when(mockSecurityContextReader.getUsername()).thenReturn("admin@gmail.com");
        when(mockInvitationRepository.findByRecipientEmailAndIsInvitationAccepted(any(String.class), any(boolean.class))).thenReturn(null);

        List<InvitationResponseDto> invitationResponseDtos = invitationRest.getReceivedInvitations();

        verify(mockInvitationRepository, times(1)).findByRecipientEmailAndIsInvitationAccepted("admin@gmail.com", false);
    }
}
