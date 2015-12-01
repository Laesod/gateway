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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class InvitationRestUnitTest {

    @Mock
    private SecurityContextReader mockSecurityContextReader;

    @Mock
    private IInvitationRepository mockInvitationRepository;

    @InjectMocks
    private InvitationRest invitationRest;

    //user story 2.
    @Test
    public void getReceivedInvitations(){
        when(mockSecurityContextReader.getUsername()).thenReturn("admin@gmail.com");
        when(mockInvitationRepository.findByRecipientEmailAndIsInvitationAccepted(any(String.class), any(boolean.class)))
                .thenReturn(null);

        List<InvitationResponseDto> invitationResponseDtos = invitationRest.getReceivedInvitations();

        verify(mockInvitationRepository, times(1)).findByRecipientEmailAndIsInvitationAccepted("admin@gmail.com", false);
    }
}
