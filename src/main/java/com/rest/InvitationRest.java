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


import com.dto.InvitationRequestDto;
import com.dto.InvitationResponseDto;
import com.entity.InvitationEntity;
import com.repository.IInvitationRepository;
import com.repository.ITranslationRepository;
import com.utils.SecurityContextReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aautushk on 9/19/2015.
 */
@Component
@RestController
@RequestMapping("/gateway")
public class InvitationRest {
    @Autowired
    public IInvitationRepository invitationRepository;

    @Autowired
    public ITranslationRepository translationRepository;

    public SecurityContextReader securityContextReader = new SecurityContextReader();

    @RequestMapping(value = "/createInvitation", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity createInvitation(@RequestBody InvitationRequestDto invitationRequestDto) {


        return new ResponseEntity(HttpStatus.OK);
    };

    @RequestMapping(value = "/getReceivedInvitations", method = RequestMethod.GET)
    @Transactional
    public List<InvitationResponseDto> getReceivedInvitations() {
        List<InvitationEntity> invitationEntities = new ArrayList<InvitationEntity>();
        List<InvitationResponseDto> invitationResponseDtos = new ArrayList<InvitationResponseDto>();

      //  invitationEntities = invitationRepository.findByRecipientEmailAndIsInvitationAccepted(securityContextReader.getUsername(), false);

        if(invitationEntities != null){
            for(InvitationEntity invitationEntity : invitationEntities){
                InvitationResponseDto invitationResponseDto = new InvitationResponseDto();
                invitationResponseDto.setInvtitationGuid(invitationEntity.getInvitationGuid());

               // TranslationEntity translationEntity = translationRepository.findByParentGuidAndFieldAndLanguage(invitationEntity.getProjectGuid(), "description", LocaleContextHolder.getLocale().getDisplayLanguage());

//                if(translationEntity != null){
//                    invitationResponseDto.setProjectDescription(translationEntity.getContent());
//                    invitationResponseDto.setCreatedBy(invitationEntity.getCreatedByUser());
//                    invitationResponseDto.setCreatedAt(invitationEntity.getCreatedAt());
//                }

                invitationResponseDtos.add(invitationResponseDto);
            }
        }

        return invitationResponseDtos;
    };
}
