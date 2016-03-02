package com.rest.entryManagement;

import com.dto.entryManagement.*;
import com.dto.userManagement.*;
import com.entity.*;
import com.entity.entryManagement.DeficiencyDetailsEntity;
import com.entity.entryManagement.EntryEntity;
import com.entity.entryManagement.EntryStatusEntity;
import com.entity.entryManagement.EntryTypeEntity;
import com.entity.userManagement.GroupEntity;
import com.entity.userManagement.ProjectEntity;
import com.entity.userManagement.RoleEntity;
import com.entity.userManagement.UserEntity;
import com.repository.*;
import com.repository.entryManagement.IDeficiencyDetailsRepository;
import com.repository.entryManagement.IEntryRepository;
import com.repository.entryManagement.IEntryStatusRepository;
import com.repository.entryManagement.IEntryTypeRepository;
import com.repository.userManagement.*;
import com.utils.BundleMessageReader;
import com.utils.PermissionsValidator;
import com.utils.SecurityContextReader;
import com.utils.TranslationManager;
import org.hibernate.validator.internal.engine.groups.Group;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public IEntryRepository entryRepository;

    @Autowired
    IDeficiencyDetailsRepository deficiencyDetailsRepository;

    @Autowired
    public IEntryTypeRepository entryTypeRepository;

    @Autowired
    public IEntryStatusRepository entryStatusRepository;

    @Autowired
    public IUserRepository userRepository;

    @Autowired
    public IGroupRepository groupRepository;

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

    @RequestMapping(value = "/getEntryStatuses", method = RequestMethod.GET)
    @Transactional
    public List<EntryStatusResponseDto> getEntryStatuses(@RequestParam String entryType) {
        //no auth check for now

        List<EntryStatusResponseDto> entryStatusResponseDtos = new ArrayList<EntryStatusResponseDto>();

        ArrayList<Object[]>  entryStatusesObj = entryStatusRepository.getEntryStatuses(entryType, LocaleContextHolder.getLocale().getDisplayLanguage());

        for (Object[] entryStatusObj : entryStatusesObj) {
            EntryStatusResponseDto entryStatusResponseDto = new EntryStatusResponseDto();
            entryStatusResponseDto.setEntryStatusGuid((String) entryStatusObj[0]);
            entryStatusResponseDto.setName((String) entryStatusObj[1]);
            entryStatusResponseDto.setIconS3ObjectKey((String) entryStatusObj[2]);
            entryStatusResponseDto.setBackgroundColor((String) entryStatusObj[3]);
            entryStatusResponseDto.setRanking((Integer) entryStatusObj[4]);
            entryStatusResponseDtos.add(entryStatusResponseDto);
        }

        return entryStatusResponseDtos;
    }

    @RequestMapping(value = "/createEntry", method = RequestMethod.POST)
    @Transactional
    public EntryResponseDto createEntry(@Valid @RequestBody EntryRequestDto entryRequestDto) {
        //auth check if user has manager role for the project
        UserEntity user = userRepository.findByUsername(securityContextReader.getUsername());
        String[] requiredRoles = {"manager"};
        if(!permissionsValidator.rolesForProjectCheck(user, entryRequestDto.getProjectGuid(), requiredRoles)){
            throw new RuntimeException(bundleMessageReader.getMessage("PermissionsRelatedIssue"));
        }

        EntryEntity entry = new EntryEntity();

        entry.setEntryType(entryTypeRepository.findByEntryTypeGuid(entryRequestDto.getEntryTypeGuid()));
        entry.setProject(projectRepository.findByProjectGuid(entryRequestDto.getProjectGuid()));
        entry.setDescription(entryRequestDto.getDescription());

        Set<GroupEntity> groups = new HashSet<GroupEntity>();
        for (String groupGuid : entryRequestDto.getGroups()) {
            GroupEntity group = groupRepository.findByGroupGuid(groupGuid);
            groups.add(group);

        }

        entry.setGroups(groups);

        entryRepository.save(entry);

        EntryResponseDto entryResponseDto = new EntryResponseDto();
        entryResponseDto.setEntryGuid(entry.getEntryGuid());

        return entryResponseDto;
    }

    @RequestMapping(value = "/updateEntry", method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity updateEntry(@Valid @RequestBody EntryRequestDto entryRequestDto) {
        //auth check if user has manager role for the project
        UserEntity user = userRepository.findByUsername(securityContextReader.getUsername());
        String[] requiredRoles = {"manager", "user"};
        if(!permissionsValidator.rolesForProjectCheck(user, entryRequestDto.getProjectGuid(), requiredRoles)){
            throw new RuntimeException(bundleMessageReader.getMessage("PermissionsRelatedIssue"));
        }

        EntryEntity entry = entryRepository.findByEntryGuid(entryRequestDto.getEntryGuid());
        GroupEntity group = new GroupEntity();
        Set<GroupEntity> entryGroups = entry.getGroups();
        String[] groups = entryRequestDto.getGroups();
        List<String> updatedGroups = Arrays.asList(groups);

        entry.setDescription(entryRequestDto.getDescription());

        if (groups != null) {
            Iterator<GroupEntity> iterator = entryGroups.iterator();
            for (String groupGuid : groups) {
                Boolean matchFound = false;

                while (iterator.hasNext()) {
                    group = iterator.next();

                    if (groupGuid.equals(group.getGroupGuid())) {
                        matchFound = true;
                        break;
                    }
                }

                if(!matchFound){
                    group = new GroupEntity();
                    group = groupRepository.findByGroupGuid(groupGuid);
                    entryGroups.add(group);
                }
            }

            iterator = entryGroups.iterator();
            while (iterator.hasNext()) {
                group = iterator.next();

                if (!updatedGroups.contains(group.getGroupGuid())) {
                    iterator.remove();
                }
            }
        }

        entryRepository.save(entry);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/createDeficiencyDetails", method = RequestMethod.POST)
    @Transactional
    public DeficiencyDetailsResponseDto createDeficiencyDetails(@Valid @RequestBody DeficiencyDetailsRequestDto deficiencyDetailsRequestDto) {
        EntryEntity entry = entryRepository.findByEntryGuid(deficiencyDetailsRequestDto.getParentEntryGuid());
        //auth check if user has manager role for the project
        UserEntity user = userRepository.findByUsername(securityContextReader.getUsername());
        String[] requiredRoles = {"manager"};
        if(!permissionsValidator.rolesForProjectCheck(user, entry.getProject().getProjectGuid(), requiredRoles)){
            throw new RuntimeException(bundleMessageReader.getMessage("PermissionsRelatedIssue"));
        }

        DeficiencyDetailsEntity deficiencyDetails = new DeficiencyDetailsEntity();

        EntryStatusEntity entryStatus = entryStatusRepository.findByEntryStatusGuid(deficiencyDetailsRequestDto.getEntryStatusGuid());
        deficiencyDetails.setEntryStatus(entryStatus);

        deficiencyDetailsRepository.save(deficiencyDetails);

        entry.setDeficiencyDetails(deficiencyDetails);

        entryRepository.save(entry);

        DeficiencyDetailsResponseDto deficiencyDetailsResponseDto = new DeficiencyDetailsResponseDto();
        deficiencyDetailsResponseDto.setDeficiencyDetailsGuid(deficiencyDetails.getDeficiencyDetailsGuid());

        return deficiencyDetailsResponseDto;
    }

    @RequestMapping(value = "/updateDeficiencyDetails", method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity updateDeficiencyDetails(@Valid @RequestBody DeficiencyDetailsRequestDto deficiencyDetailsRequestDto) {
        EntryEntity entry = entryRepository.findByEntryGuid(deficiencyDetailsRequestDto.getParentEntryGuid());
        //auth check if user has manager role for the project
        UserEntity user = userRepository.findByUsername(securityContextReader.getUsername());
        String[] requiredRoles = {"manager", "user"};
        if(!permissionsValidator.rolesForProjectCheck(user, entry.getProject().getProjectGuid(), requiredRoles)){
            throw new RuntimeException(bundleMessageReader.getMessage("PermissionsRelatedIssue"));
        }

        DeficiencyDetailsEntity deficiencyDetail = deficiencyDetailsRepository.findByDeficiencyDetailsGuid(deficiencyDetailsRequestDto.getDeficiencyDetailsGuid());
        EntryStatusEntity entryStatus = entryStatusRepository.findByEntryStatusGuid(deficiencyDetailsRequestDto.getEntryStatusGuid());
        deficiencyDetail.setEntryStatus(entryStatus);

        deficiencyDetailsRepository.save(deficiencyDetail);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/getEntries", method = RequestMethod.GET)
    @Transactional
    public Page<EntryResponseDto> getEntries(Pageable pageable) {
        List<EntryResponseDto> entries = new ArrayList<EntryResponseDto>();

        Page<Object[]> entriesObj = entryRepository.getEntries(LocaleContextHolder.getLocale().getDisplayLanguage(), pageable);
        Long entiesNumber = entriesObj.getTotalElements();

        for (Object[] entryObj : entriesObj.getContent()) {
            EntryResponseDto entryResponseDto = new EntryResponseDto();
            String entryGuid = (String) entryObj[0];
            String entryTypeGuid = (String) entryObj[2];

            entryResponseDto.setEntryGuid(entryGuid);
            entryResponseDto.setDescription((String) entryObj[1]);
            entryResponseDto.setEntryTypeGuid(entryTypeGuid);
            entryResponseDto.setEntryTypeName((String) entryObj[3]);

            EntryEntity entry = entryRepository.findByEntryGuid(entryGuid);
            Set<GroupEntity> groups = entry.getGroups();
            List<GroupResponseDto> groupsList = new ArrayList<GroupResponseDto>();

            for (GroupEntity group : groups){
                GroupResponseDto groupResponseDto = new GroupResponseDto();
                groupResponseDto.setGroupGuid(group.getGroupGuid());
                groupResponseDto.setGroupName(group.getGroupName());

                groupsList.add(groupResponseDto);
            }

            entryResponseDto.setGroups(groupsList);
            entries.add(entryResponseDto);

            if(entryTypeGuid.equals("1")){//in case of deficiency
                DeficiencyDetailsEntity deficiencyDetails = entry.getDeficiencyDetails();

                if(deficiencyDetails != null){
                    List<Object[]> deficiencyDetailsObjList = deficiencyDetailsRepository.getDeficiencyDetails(deficiencyDetails    .getDeficiencyDetailsGuid(),  LocaleContextHolder.getLocale().getDisplayLanguage());
                    Object[] deficiencyDetailsObj;
                    if(deficiencyDetailsObjList !=null && deficiencyDetailsObjList.size() > 0){
                        deficiencyDetailsObj = deficiencyDetailsObjList.get(0);

                        DeficiencyDetailsResponseDto deficiencyDetailsResponseDto = new DeficiencyDetailsResponseDto();

                        deficiencyDetailsResponseDto.setDeficiencyDetailsGuid((String) deficiencyDetailsObj[0]);
                        deficiencyDetailsResponseDto.setEntryStatusGuid((String) deficiencyDetailsObj[1]);
                        deficiencyDetailsResponseDto.setEntryStatusName((String) deficiencyDetailsObj[2]);
                        deficiencyDetailsResponseDto.setEntryStatusBackgroundColor((String) deficiencyDetailsObj[3]);

                        entryResponseDto.setDeficiencyDetails(deficiencyDetailsResponseDto);
                    }
                }

            }

        }

        Page<EntryResponseDto> page = new PageImpl<>(entries, pageable, entiesNumber);
        return page;
    }
}