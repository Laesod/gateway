package com.rest;

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


import com.dto.*;
import com.entity.AuthorityEntity;
import com.entity.GroupEntity;
import com.entity.RoleEntity;
import com.entity.UserEntity;
import com.repository.IAuthorityRepository;
import com.repository.IUserRepository;
import com.utils.BundleMessageReader;
import com.utils.SecurityContextReader;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.validation.Valid;
import javax.validation.Validator;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

;

/**
 * Created by aautushk on 8/30/2015.
 */


@Component
@RestController
@RequestMapping("/gateway")
public class UserRest {
    @Autowired
	public IUserRepository userRepository;

    @Autowired
    public IAuthorityRepository authorityRepository;

    private BundleMessageReader bundleMessageReader = new BundleMessageReader();

    public SecurityContextReader securityContextReader = new SecurityContextReader();

    @Autowired
    public Validator validator;

    @Autowired
    public JavaMailSender mailSender;

    @Autowired
    public ClassLoaderTemplateResolver emailTemplateResolver;

    @Autowired
    public SpringTemplateEngine thymeleaf;

    @Value("${mailserver.sendFrom}")
    public String mailSendFrom;

    @Value("${gatewayHost}")
    public String gatewayHost;

    @Value("${gatewayPort}")
    public String gatewayPort;

    @Value("${server.contextPath}")
    public String contextPath;

    public EmailSender emailSender;

    private ModelMapper modelMapper = new ModelMapper(); //read more at http://modelmapper.org/user-manual/

    private StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder("53cr3t");

    private UserResponseDto convertToResponseDto(UserEntity user) {
        UserResponseDto userDro = modelMapper.map(user, UserResponseDto.class);
        return userDro;
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity createUser(@Valid @RequestBody UserRequestDto userRequestDto){
        UserEntity user = userRepository.findByUsername(userRequestDto.getUsername());
        if(user != null){
            throw new RuntimeException(bundleMessageReader.getMessage("UserAlreadyRegistered"));
        }

        String encodedPassword = standardPasswordEncoder.encode(userRequestDto.getPassword());
        userRequestDto.setPassword(encodedPassword);

        user = modelMapper.map(userRequestDto, UserEntity.class);
        user.setEnabled(false);

        UUID emailVerificationToken = UUID.randomUUID();
        user.setEmailVerificationToken(emailVerificationToken.toString());

        userRepository.save(user);

        AuthorityEntity authorityEntity = new AuthorityEntity();
        authorityEntity.setUsername(user.getUsername());
        authorityEntity.setAuthority("SYSTEM_USER");

        authorityRepository.save(authorityEntity); // initial role is assigned to the new user (needed by spring security)

        if(emailSender == null){// this check needed for unit testing perposes
            emailSender = new EmailSender(mailSender, emailTemplateResolver, thymeleaf, user.getUsername(), mailSendFrom);
        }

        String requestBaseUrl = this.gatewayHost + ':' + this.gatewayPort + this.contextPath;
        emailSender.sendVerificationTokenEmail(emailVerificationToken.toString(), requestBaseUrl);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/activateUser", method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity activateUser(@RequestParam String emailVerificationToken) {
        UserEntity user = userRepository.findByEmailVerificationToken(emailVerificationToken);
        if(user == null){
            throw new RuntimeException(bundleMessageReader.getMessage("NotValidEmailVerificationToken"));
        }
        user.setEnabled(true);
        userRepository.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity changePassword(@Valid @RequestBody UserPasswordRequestDto userPasswordRequestDto, Principal user) {
        String username =  user.getName();

        UserEntity userFromDB = userRepository.findByUsername(username);

        if(!standardPasswordEncoder.matches(userPasswordRequestDto.getCurrentPassword(), userFromDB.getPassword())){
            throw new RuntimeException(bundleMessageReader.getMessage("WrongCurrentPassword"));
        }

        String encodedNewPassword = standardPasswordEncoder.encode(userPasswordRequestDto.getNewPassword());
        userFromDB.setPassword(encodedNewPassword);

        userRepository.save(userFromDB);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/initiateResetPassword", method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity initiateResetPassword(@Valid @RequestBody InitiateResetPasswordRequestDto initiateResetPasswordRequestDto) {
        UserEntity user = userRepository.findByUsername(initiateResetPasswordRequestDto.getEmail());

        if(user == null){
            throw new RuntimeException(bundleMessageReader.getMessage("NoUserFound"));
        }

        UUID resetPasswordToken = UUID.randomUUID();
        user.setResetPasswordToken(resetPasswordToken.toString());

        userRepository.save(user);

        if(emailSender == null){// this check needed for unit testing perposes
            emailSender = new EmailSender(mailSender, emailTemplateResolver, thymeleaf, user.getUsername(), mailSendFrom);
        }

        String requestBaseUrl = this.gatewayHost + ':' + this.gatewayPort + this.contextPath;
        emailSender.sendReserPasswordEmail(resetPasswordToken.toString(), requestBaseUrl);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity resetPassword(@Valid @RequestBody ResetPasswordRequestDto resetPasswordDto, @RequestParam String resetPasswordToken) {
        UserEntity user = userRepository.findByResetPasswordToken(resetPasswordToken);

        if(user == null){
            throw new RuntimeException(bundleMessageReader.getMessage("NotValidResetPasswordToken"));
        }

        String encodedNewPassword = standardPasswordEncoder.encode(resetPasswordDto.getNewPassword());
        user.setPassword(encodedNewPassword);

        userRepository.save(user);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/getUserProfile", method = RequestMethod.GET)
    public UserProfileDto getUserProfile() {
        UserProfileDto userProfileDto = new UserProfileDto();
        List<UserProjectResponseDto> userProjectResponseDtos = new ArrayList<UserProjectResponseDto>();

        UserEntity userEntity = userRepository.findByUsername(securityContextReader.getUsername());
        if(userEntity == null){
            return userProfileDto;
        }
        ArrayList<Object[]> userProjects = userRepository.getUserProjects(securityContextReader.getUsername(), LocaleContextHolder.getLocale().getDisplayLanguage());

        for (Object[] userProject : userProjects) {
            UserProjectResponseDto userProjectResponseDto = new UserProjectResponseDto();

            List<RoleResponseDto> roles = new ArrayList<>();
            if(userEntity.getRoles() != null){
                for (RoleEntity role : userEntity.getRoles()) {
                    if (userProject[0].equals(role.getProject().getProjectGuid())) {
                        RoleResponseDto roleResponseDto = new RoleResponseDto();
                        roleResponseDto.setRoleGuid(role.getRoleGuid());
                        roleResponseDto.setRoleName(role.getRoleName());

                        roles.add(roleResponseDto);
                    }
                }
            }
            userProjectResponseDto.setRoles(roles);

            List<GroupResponseDto> groups = new ArrayList<>();
            if(userEntity.getGroups() != null){
                for (GroupEntity group : userEntity.getGroups()) {
                    if ((group.getProject() != null && group.getProject().getProjectGuid().equals((String) userProject[0]))) {
                        GroupResponseDto groupResponseDto = new GroupResponseDto();
                        groupResponseDto.setGroupGuid(group.getGroupGuid());
                        groupResponseDto.setGroupName(group.getGroupName());
                        groups.add(groupResponseDto);
                    }
                }
                userProjectResponseDto.setGroups(groups);
            }

            userProjectResponseDto.setProjectGuid((String) userProject[0]);
            userProjectResponseDto.setProjectDescription((String) userProject[1]);


            userProjectResponseDtos.add(userProjectResponseDto);
        }

        List<GroupResponseDto> userGroups = new ArrayList<>();

        userProfileDto.setUsername(userEntity.getUsername());
        userProfileDto.setFirstName(userEntity.getFirstName());
        userProfileDto.setLastName(userEntity.getLastName());
        userProfileDto.setAvatarS3ObjectKey(userEntity.getAvatarS3ObjectKey());
        userProfileDto.setUserProjects(userProjectResponseDtos);

        return userProfileDto;
    }

    @RequestMapping(value = "/changeAvatar", method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity changeAvatar(@RequestParam String s3ObjectKey, Principal user) throws IOException {
        UserEntity userEntity = userRepository.findByUsername(user.getName());

        userEntity.setAvatarS3ObjectKey(s3ObjectKey);
        userRepository.save(userEntity);

        return new ResponseEntity(HttpStatus.OK);
    }
}
