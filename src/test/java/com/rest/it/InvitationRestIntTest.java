package com.rest.it;

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


import com.MockGatewayApplication;
import com.repository.userManagement.IInvitationRepository;
import com.repository.ITranslationRepository;
import com.rest.userManagement.InvitationRest;
import com.utils.SecurityContextReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockGatewayApplication.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback=true)
@ActiveProfiles("test")
public class InvitationRestIntTest {

    @Autowired
    private IInvitationRepository invitationRepository;

    @Autowired
    private ITranslationRepository translationRepository;

    @Mock
    private SecurityContextReader securityContextReaderMock;

    @InjectMocks
    private InvitationRest invitationRest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    //user story 2.
    @Test
    public void getReceivedInvitations() {
        when(securityContextReaderMock.getUsername()).thenReturn("test@gmail.com");

//        InvitationEntity invitationEntity = new InvitationEntity();
//        invitationEntity.setRecipientEmail("test@gmail.com");
//        invitationEntity.setAuthority("123_write");
//        invitationEntity.setIsInvitationAccepted(false);
//        invitationEntity.setProjectGuid("123");
//
//        invitationRepository.save(invitationEntity);
//
//        List<InvitationResponseDto> invitations = new ArrayList<InvitationResponseDto>();
//
//        invitationRest.invitationRepository = invitationRepository;
//        invitationRest.translationRepository = translationRepository;
//
//        invitations = invitationRest.getReceivedInvitations();
//
//        assertEquals(invitations.size(), 1);
    }
}
