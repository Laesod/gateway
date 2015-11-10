package com.rest.unit;

import com.dto.ResetPasswordRequestDto;
import com.dto.UserPasswordRequestDto;
import com.dto.UserRequestDto;
import com.entity.AuthorityEntity;
import com.entity.UserEntity;
import com.repository.IAuthorityRepository;
import com.repository.IUserRepository;
import com.rest.EmailSender;
import com.rest.UserRest;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import java.security.Principal;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by aautushk on 10/9/2015.
 */
public class UserRestUnitTest {
    UserRest userRest = new UserRest();
    UserRequestDto userRequestDto = new UserRequestDto();
    IUserRepository mockUserRepository = Mockito.mock(IUserRepository.class);
    IAuthorityRepository mockAuthorityRepository = Mockito.mock(IAuthorityRepository.class);
    StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder("53cr3t");
    EmailSender mockEmailSender = Mockito.mock(EmailSender.class);
    Principal mockPrincipalUser = Mockito.mock(Principal.class);
    UserEntity user = new UserEntity();
    UserPasswordRequestDto userPasswordRequestDto = new UserPasswordRequestDto();
    ResetPasswordRequestDto resetPasswordRequestDto = new ResetPasswordRequestDto();

    public UserRestUnitTest(){
        userRest.userRepository = mockUserRepository;
        userRest.authorityRepository = mockAuthorityRepository;
        userRest.emailSender = mockEmailSender;

        userPasswordRequestDto.setCurrentPassword("password");
        userPasswordRequestDto.setNewPassword("newPassword");
        resetPasswordRequestDto.setNewPassword("password");
    }

    //user story 3.
    @Test
    public void createUser(){
        userRequestDto.setPassword("password");
        when(mockUserRepository.findByUsername(any(String.class))).thenReturn(null);

        Mockito.doNothing().when(mockEmailSender).sendVerificationTokenEmail(any(String.class), any(String.class));

        ResponseEntity responseEntity = userRest.createUser(userRequestDto);

        verify(mockUserRepository, times(1)).save(any(UserEntity.class));
        verify(mockAuthorityRepository, times(1)).save(any(AuthorityEntity.class));
        verify(mockEmailSender, times(1)).sendVerificationTokenEmail(any(String.class), any(String.class));
    }

    //user story 3.
    @Test(expected = RuntimeException.class)
    public void createUserWithExistingName(){
        when(mockUserRepository.findByUsername(any(String.class))).thenReturn(new UserEntity());

        Mockito.doNothing().when(mockEmailSender).sendVerificationTokenEmail(any(String.class), any(String.class));

        ResponseEntity responseEntity = userRest.createUser(userRequestDto);
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
        when(mockUserRepository.findByUsername(any(String.class))).thenReturn(user);
        Mockito.doNothing().when(mockEmailSender).sendReserPasswordEmail(any(String.class), any(String.class));

        userRest.initiateResetPassword("test@gmail.com");
        verify(mockUserRepository, times(1)).save(any(UserEntity.class));
        verify(mockEmailSender, times(1)).sendReserPasswordEmail(any(String.class), any(String.class));
    }

    //user story 5.
    @Test(expected = RuntimeException.class)
    public void initiateResetPasswordNotValidEmail(){
        when(mockUserRepository.findByUsername(any(String.class))).thenReturn(null);

        userRest.initiateResetPassword("test@gmail.com");
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