package com.rest.unit;

/*
 * #%L
 * Gateway
 * %%
 * Copyright (C) 2015 Powered by Sergey
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


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
