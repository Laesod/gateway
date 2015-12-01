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
import com.dto.InitiateResetPasswordRequestDto;
import com.dto.ResetPasswordRequestDto;
import com.dto.UserPasswordRequestDto;
import com.dto.UserRequestDto;
import com.entity.AuthorityEntity;
import com.entity.UserEntity;
import com.repository.IAuthorityRepository;
import com.repository.IUserRepository;
import com.rest.UserRest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

class TestData{
    public UserRequestDto prepareUserRequestDto(){
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setUsername("test@gmail.com");
        userRequestDto.setFirstName("tester");
        userRequestDto.setLastName("tester");
        userRequestDto.setPassword("password");

        return userRequestDto;
    }
}

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockGatewayApplication.class)
@WebAppConfiguration
@Transactional
@TransactionConfiguration(defaultRollback=true)
@ActiveProfiles("test")
public class UserRestIntTest {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IAuthorityRepository authorityRepository;

    @Autowired
    private ClassLoaderTemplateResolver emailTemplateResolver;

    @Autowired
    private SpringTemplateEngine thymeleaf;

    @Value("${mailserver.sendFrom}")
    private String mailSendFrom;

    private TestData testData = new TestData();

    UserRest userRest = new UserRest();

    private StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder("53cr3t");

    private UserEntity userEntity = new UserEntity();

    @Mock
    private JavaMailSender mailSender;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(mailSender.createMimeMessage())
                .thenReturn(new MimeMessage(Session.getDefaultInstance(new Properties())));
    }

    //user story 3.
    @Test
    public void createUser() throws MessagingException {
        UserRequestDto userRequestDto = testData.prepareUserRequestDto();

        userRest.userRepository = userRepository;
        userRest.authorityRepository = authorityRepository;

        userRest.mailSender = mailSender;
        userRest.emailTemplateResolver = emailTemplateResolver;
        userRest.thymeleaf = thymeleaf;
        userRest.mailSendFrom = mailSendFrom;

        userRest.createUser(userRequestDto);

        userEntity = userRepository.findByUsername("test@gmail.com");

        List<AuthorityEntity> authorities = new ArrayList<AuthorityEntity>();
        authorities = authorityRepository.findByUsername("test@gmail.com");

        assertEquals(userEntity.getUsername(), "test@gmail.com");
        assertEquals(userEntity.isEnabled(), false);
        assertEquals(authorities.size(), 1);
        assertEquals(authorities.get(0).getAuthority(), "SYSTEM_USER");
    }

    //user story 3.
    @Test
    public void activateUser(){
        UserRequestDto userRequestDto = testData.prepareUserRequestDto();

        userRest.userRepository = userRepository;
        userRest.authorityRepository = authorityRepository;

        userRest.mailSender = mailSender;
        userRest.emailTemplateResolver = emailTemplateResolver;
        userRest.thymeleaf = thymeleaf;
        userRest.mailSendFrom = mailSendFrom;

        userRest.createUser(userRequestDto);

        userEntity = userRepository.findByUsername("test@gmail.com");
        userRest.activateUser(userEntity.getEmailVerificationToken());

        userEntity = userRepository.findByUsername("test@gmail.com");

        assertEquals(userEntity.isEnabled(), true);
    }

    //user story 4.
    @Test
    public void changePassword() {
        userEntity = userRepository.findByUsername("test@gmail.com");

        if(userEntity == null){
            userEntity = new UserEntity();
            userEntity.setFirstName("tester");
            userEntity.setLastName("tester");
            userEntity.setPassword(standardPasswordEncoder.encode("password1"));
            userEntity.setEnabled(true);
            userEntity.setUsername("test@gmail.com");

            userRepository.save(userEntity);// needed to be able to save authority
        }

        Principal userMock = Mockito.mock(Principal.class);
        when(userMock.getName()).thenReturn("test@gmail.com");

        userRest.userRepository = userRepository;
        UserPasswordRequestDto userPasswordDto = new UserPasswordRequestDto();
        userPasswordDto.setCurrentPassword("password1");
        userPasswordDto.setNewPassword("password2");
        userRest.changePassword(userPasswordDto, userMock);

        UserEntity user = userRepository.findByUsername("test@gmail.com");
        boolean isPasswordChanged = false;
        if(standardPasswordEncoder.matches("password2", user.getPassword())){
            isPasswordChanged = true;
        }
        assertEquals(isPasswordChanged, true);
    }

    //user story 5.
    @Test
    public void resetPassword() {
        UserRequestDto userRequestDto = testData.prepareUserRequestDto();

        userRest.userRepository = userRepository;
        userRest.authorityRepository = authorityRepository;

        userRest.mailSender = mailSender;
        userRest.emailTemplateResolver = emailTemplateResolver;
        userRest.thymeleaf = thymeleaf;
        userRest.mailSendFrom = mailSendFrom;

        userRest.createUser(userRequestDto);

        InitiateResetPasswordRequestDto initiateResetPasswordRequestDto = new InitiateResetPasswordRequestDto();
        initiateResetPasswordRequestDto.setEmail(userRequestDto.getUsername());
        userRest.initiateResetPassword(initiateResetPasswordRequestDto);

        userEntity = userRepository.findByUsername("test@gmail.com");

        ResetPasswordRequestDto resetPasswordRequestDto = new ResetPasswordRequestDto();
        resetPasswordRequestDto.setNewPassword("newPassword");
        userRest.resetPassword(resetPasswordRequestDto, userEntity.getResetPasswordToken());

        userEntity = userRepository.findByUsername("test@gmail.com");
        boolean isPasswordReset = false;
        if(standardPasswordEncoder.matches("newPassword", userEntity.getPassword())){
            isPasswordReset = true;
        }
        assertEquals(isPasswordReset, true);
    }
}

