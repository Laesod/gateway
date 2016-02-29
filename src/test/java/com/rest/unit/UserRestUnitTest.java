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


import com.dto.userManagement.InitiateResetPasswordRequestDto;
import com.dto.userManagement.ResetPasswordRequestDto;
import com.dto.userManagement.UserPasswordRequestDto;
import com.dto.userManagement.UserRequestDto;
import com.entity.userManagement.UserEntity;
import com.repository.userManagement.IAuthorityRepository;
import com.repository.userManagement.IUserRepository;
import com.rest.EmailSender;
import com.rest.userManagement.UserRest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import java.security.Principal;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserRestUnitTest {

    @Mock
    private IUserRepository mockUserRepository;

    @Mock
    private IAuthorityRepository mockAuthorityRepository;

    @Mock
    private EmailSender mockEmailSender;

    @Mock
    private Principal mockPrincipalUser;

    @InjectMocks
    private UserRest userRest;

    private UserRequestDto userRequestDto = new UserRequestDto();
    private StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder("53cr3t");
    private UserEntity user = new UserEntity();
    private UserPasswordRequestDto userPasswordRequestDto = new UserPasswordRequestDto();
    private ResetPasswordRequestDto resetPasswordRequestDto = new ResetPasswordRequestDto();
    private InitiateResetPasswordRequestDto initiateResetPasswordRequestDto = new InitiateResetPasswordRequestDto();

    @Before
    public void setup(){
        userPasswordRequestDto.setCurrentPassword("password");
        userPasswordRequestDto.setNewPassword("newPassword");
        resetPasswordRequestDto.setNewPassword("password");
    }

    //user story 3.
    @Test
    public void createUser(){
//        userRequestDto.setPassword("password");
//        when(mockUserRepository.findByUsername(any(String.class))).thenReturn(null);
//
//        Mockito.doNothing().when(mockEmailSender).sendVerificationTokenEmail(any(String.class), any(String.class));
//
//        ResponseEntity responseEntity = userRest.createUser(userRequestDto);
//
//        verify(mockUserRepository, times(1)).save(any(UserEntity.class));
//        verify(mockAuthorityRepository, times(1)).save(any(AuthorityEntity.class));
//        verify(mockEmailSender, times(1)).sendVerificationTokenEmail(any(String.class), any(String.class));
    }

    //user story 3.
    @Test(expected = RuntimeException.class)
    public void createUserWithExistingName(){
//        when(mockUserRepository.findByUsername(any(String.class))).thenReturn(new UserEntity());
//
//        Mockito.doNothing().when(mockEmailSender).sendVerificationTokenEmail(any(String.class), any(String.class));
//
//        ResponseEntity responseEntity = userRest.createUser(userRequestDto);
    }

    //user story 3.
    @Test
    public void activateUser(){
        user.setEnabled(false);

        when(mockUserRepository.findByEmailVerificationToken(any(String.class))).thenReturn(user);

        userRest.activateUser("dummy email verification token");

        assertTrue(user.isEnabled());
        verify(mockUserRepository, times(1)).save(any(UserEntity.class));
    }

    //user story 3.
    @Test(expected = RuntimeException.class)
    public void activateUserNotValidEmailVerificationToken(){
        when(mockUserRepository.findByEmailVerificationToken(any(String.class))).thenReturn(null);

        userRest.activateUser("dummy uuid");
    }

    //user story 4.
    @Test
    public void changePassword(){
        user.setPassword(standardPasswordEncoder.encode("password"));
        when(mockUserRepository.findByUsername(any(String.class))).thenReturn(user);

        when(mockPrincipalUser.getName()).thenReturn("test@gmail.com");

        userRest.changePassword(userPasswordRequestDto, mockPrincipalUser);

        verify(mockUserRepository, times(1)).findByUsername(any(String.class));
        verify(mockUserRepository, times(1)).save(any(UserEntity.class));
    }

    //user story 4.
    @Test(expected = RuntimeException.class)
    public void chantePasswordNotValidCurrentPassword(){
        user.setPassword(standardPasswordEncoder.encode("!!!password"));
        when(mockUserRepository.findByUsername(any(String.class))).thenReturn(user);

        when(mockPrincipalUser.getName()).thenReturn("test@gmail.com");

        userRest.changePassword(userPasswordRequestDto, mockPrincipalUser);
    }

    //user story 5.
    @Test
    public void initiateResetPassword(){
//        when(mockUserRepository.findByUsername(any(String.class))).thenReturn(user);
//        Mockito.doNothing().when(mockEmailSender).sendReserPasswordEmail(any(String.class), any(String.class));
//
//        initiateResetPasswordRequestDto.setEmail("test@gmail.com");
//        userRest.initiateResetPassword(initiateResetPasswordRequestDto);
//        verify(mockUserRepository, times(1)).save(any(UserEntity.class));
//        verify(mockEmailSender, times(1)).sendReserPasswordEmail(any(String.class), any(String.class));
    }

    //user story 5.
    @Test(expected = RuntimeException.class)
    public void initiateResetPasswordNotValidEmail(){
        when(mockUserRepository.findByUsername(any(String.class))).thenReturn(null);

        initiateResetPasswordRequestDto.setEmail("test@gmail.com");
        userRest.initiateResetPassword(initiateResetPasswordRequestDto);
    }

    //user story 5.
    @Test
    public void resetPassword(){
        when(mockUserRepository.findByResetPasswordToken(any(String.class))).thenReturn(user);
        userRest.resetPassword(resetPasswordRequestDto, "dummy reset password token");

        verify(mockUserRepository, times(1)).save(any(UserEntity.class));
    }

    //user story 5.
    @Test(expected = RuntimeException.class)
    public void resetPasswordNotValidToken(){
        when(mockUserRepository.findByResetPasswordToken(any(String.class))).thenReturn(null);

        userRest.resetPassword(resetPasswordRequestDto, "dummy reset password token");
    }
}