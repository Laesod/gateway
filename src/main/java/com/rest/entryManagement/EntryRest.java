package com.rest.entryManagement;

import com.dto.entryManagement.EntryTypeResponseDto;
import com.dto.userManagement.*;
import com.entity.*;
import com.entity.entryManagement.EntryTypeEntity;
import com.entity.userManagement.GroupEntity;
import com.entity.userManagement.ProjectEntity;
import com.entity.userManagement.RoleEntity;
import com.entity.userManagement.UserEntity;
import com.repository.*;
import com.repository.entryManagement.IEntryTypeRepository;
import com.repository.userManagement.*;
import com.utils.BundleMessageReader;
import com.utils.PermissionsValidator;
import com.utils.SecurityContextReader;
import com.utils.TranslationManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * Created by aautushk on 9/13/2015.
 */
@Component
@RestController
@RequestMapping("/entry")
public class EntryRest {
    private ModelMapper modelMapper = new ModelMapper(); //read more at http://modelmapper.org/user-manual/

    @Autowired
    public IEntryTypeRepository entryTypeRepository;

    @Autowired
    public IUserRepository userRepository;

    @Autowired
    public IProjectRepository projectRepository;

    public SecurityContextReader securityContextReader = new SecurityContextReader();

    public PermissionsValidator permissionsValidator = new PermissionsValidator();

    public BundleMessageReader bundleMessageReader = new BundleMessageReader();

    public TranslationManager translationManager = new TranslationManager();

    @RequestMapping(value = "/getEntryTypesForProject", method = RequestMethod.GET)
    @Transactional
    public List<EntryTypeResponseDto>  getEntryTypesForProject(@RequestParam String projectGuid) {
        //auth check if user is assigned to the project
        UserEntity user = userRepository.findByUsername(securityContextReader.getUsername());
        if(!permissionsValidator.projectCheck(user, projectGuid)){
            throw new RuntimeException(bundleMessageReader.getMessage("PermissionsRelatedIssue"));
        }

        List<EntryTypeResponseDto> entryTypeResponseDtos = new ArrayList<EntryTypeResponseDto>();

        ArrayList<Object[]>  entryTypesObj = projectRepository.getProjectEntryTypes(projectGuid, LocaleContextHolder.getLocale().getDisplayLanguage());

        for (Object[] entryTypeObj : entryTypesObj) {
            EntryTypeResponseDto entryTypeResponseDto = new EntryTypeResponseDto();
            entryTypeResponseDto.setEntryTypeGuid((String) entryTypeObj[0]);
            entryTypeResponseDto.setName((String) entryTypeObj[1]);
            entryTypeResponseDtos.add(entryTypeResponseDto);
        }

        return entryTypeResponseDtos;
    }


}