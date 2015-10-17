package com.rest;

import com.dto.InvitationResponseDto;
import com.entity.InvitationEntity;
import com.entity.TranslationEntity;
import com.repository.IInvitationRepository;
import com.repository.ITranslationRepository;
import com.utils.SecurityContextReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "/getReceivedInvitations", method = RequestMethod.GET)
    @Transactional
    public List<InvitationResponseDto> getReceivedInvitations() {
        List<InvitationEntity> invitationEntities = new ArrayList<InvitationEntity>();
        List<InvitationResponseDto> invitationResponseDtos = new ArrayList<InvitationResponseDto>();

        invitationEntities = invitationRepository.findByRecipientEmailAndIsInvitationAccepted(securityContextReader.getUsername(), false);

        if(invitationEntities != null){
            for(InvitationEntity invitationEntity : invitationEntities){
                InvitationResponseDto invitationResponseDto = new InvitationResponseDto();
                invitationResponseDto.setInvtitationGuid(invitationEntity.getInvitationGuid());

                TranslationEntity translationEntity = translationRepository.findByParentGuidAndFieldAndLanguage(invitationEntity.getProjectGuid(), "description", LocaleContextHolder.getLocale().getDisplayLanguage());

                if(translationEntity != null){
                    invitationResponseDto.setProjectDescription(translationEntity.getContent());
                    invitationResponseDto.setCreatedBy(invitationEntity.getCreatedByUser());
                    invitationResponseDto.setCreatedAt(invitationEntity.getCreatedAt());
                }

                invitationResponseDtos.add(invitationResponseDto);
            }
        }

        return invitationResponseDtos;
    };
}
