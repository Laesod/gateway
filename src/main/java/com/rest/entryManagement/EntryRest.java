package com.rest.entryManagement;

import com.dto.entryManagement.*;
import com.dto.userManagement.*;
import com.entity.*;
import com.entity.entryManagement.*;
import com.entity.userManagement.GroupEntity;
import com.entity.userManagement.ProjectEntity;
import com.entity.userManagement.RoleEntity;
import com.entity.userManagement.UserEntity;
import com.repository.*;
import com.repository.entryManagement.*;
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
import org.springframework.security.crypto.password.StandardPasswordEncoder;
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
    @Autowired
    public IEntryRepository entryRepository;

    @Autowired
    IDeficiencyDetailsRepository deficiencyDetailsRepository;

    @Autowired
    IContactDetailsRepository contactDetailsRepository;

    @Autowired
    public IEntryTypeRepository entryTypeRepository;

    @Autowired
    public IEntryStatusRepository entryStatusRepository;

    @Autowired
    public IContactTypeRepository contactTypeRepository;

    @Autowired
    public IUserRepository userRepository;

    @Autowired
    public IGroupRepository groupRepository;

    @Autowired
    public IProjectRepository projectRepository;

 //   public SecurityContextReader securityContextReader = new SecurityContextReader();

    public PermissionsValidator permissionsValidator = new PermissionsValidator();

    public BundleMessageReader bundleMessageReader = new BundleMessageReader();

    public TranslationManager translationManager = new TranslationManager();

    private ModelMapper modelMapper = new ModelMapper(); //read more at http://modelmapper.org/user-manual/

    @RequestMapping(value = "/getEntryTypes", method = RequestMethod.GET)
    @Transactional
    public List<EntryTypeResponseDto>  getEntryTypes() {
        List<EntryTypeResponseDto> entryTypeResponseDtos = new ArrayList<EntryTypeResponseDto>();

        ArrayList<Object[]>  entryTypesObj = entryTypeRepository.getEntryTypes(LocaleContextHolder.getLocale().getDisplayLanguage());

        for (Object[] entryTypeObj : entryTypesObj) {
            EntryTypeResponseDto entryTypeResponseDto = new EntryTypeResponseDto();
            entryTypeResponseDto.setEntryTypeGuid((String) entryTypeObj[0]);
            entryTypeResponseDto.setName((String) entryTypeObj[1]);
            entryTypeResponseDtos.add(entryTypeResponseDto);
        }

        return entryTypeResponseDtos;
    }

    @RequestMapping(value = "/getEntryTypesForProject", method = RequestMethod.GET)
    @Transactional
    public List<EntryTypeResponseDto>  getEntryTypesForProject(@RequestParam String projectGuid) {
        //auth check if user is assigned to the project
        UserEntity user = SecurityContextReader.getUserEntity(userRepository);// userRepository.findByUsername(securityContextReader.getUsername());
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
        UserEntity user = SecurityContextReader.getUserEntity(userRepository);//userRepository.findByUsername(securityContextReader.getUsername());
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
        UserEntity user = SecurityContextReader.getUserEntity(userRepository);//userRepository.findByUsername(securityContextReader.getUsername());
        String[] requiredRoles = {"manager", "user"};
        if(!permissionsValidator.rolesForProjectCheck(user, entryRequestDto.getProjectGuid(), requiredRoles)){
            throw new RuntimeException(bundleMessageReader.getMessage("PermissionsRelatedIssue"));
        }

        if(entryRequestDto.getMarkedAsDeleted() != null && entryRequestDto.getMarkedAsDeleted().equals(true)){
            String[] requiredRolesManager = {"manager"};
            if(!permissionsValidator.rolesForProjectCheck(user, entryRequestDto.getProjectGuid(), requiredRolesManager)){
                throw new RuntimeException(bundleMessageReader.getMessage("PermissionsRelatedIssue"));
            }
        }

        EntryEntity entry = entryRepository.findByEntryGuid(entryRequestDto.getEntryGuid());
        GroupEntity group = new GroupEntity();
        Set<GroupEntity> entryGroups = entry.getGroups();
        String[] groups = entryRequestDto.getGroups();
        List<String> updatedGroups = Arrays.asList(groups);

        entry.setDescription(entryRequestDto.getDescription());
        entry.setMarkedAsDeleted(entryRequestDto.getMarkedAsDeleted());

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
        UserEntity user = SecurityContextReader.getUserEntity(userRepository);//userRepository.findByUsername(securityContextReader.getUsername());
        String[] requiredRoles = {"manager"};
        if(!permissionsValidator.rolesForProjectCheck(user, entry.getProject().getProjectGuid(), requiredRoles)){
            throw new RuntimeException(bundleMessageReader.getMessage("PermissionsRelatedIssue"));
        }

        DeficiencyDetailsEntity deficiencyDetails = new DeficiencyDetailsEntity();

        EntryStatusEntity entryStatus = entryStatusRepository.findByEntryStatusGuid(deficiencyDetailsRequestDto.getEntryStatusGuid());
        deficiencyDetails.setEntryStatus(entryStatus);
        deficiencyDetails.setDueDate(deficiencyDetailsRequestDto.getDueDate());

       // deficiencyDetailsRepository.save(deficiencyDetails);

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
        UserEntity user = SecurityContextReader.getUserEntity(userRepository);//userRepository.findByUsername(securityContextReader.getUsername());
        String[] requiredRoles = {"manager", "user"};
        if(!permissionsValidator.rolesForProjectCheck(user, entry.getProject().getProjectGuid(), requiredRoles)){
            throw new RuntimeException(bundleMessageReader.getMessage("PermissionsRelatedIssue"));
        }

        DeficiencyDetailsEntity deficiencyDetail = deficiencyDetailsRepository.findByDeficiencyDetailsGuid(deficiencyDetailsRequestDto.getDeficiencyDetailsGuid());
        EntryStatusEntity entryStatus = entryStatusRepository.findByEntryStatusGuid(deficiencyDetailsRequestDto.getEntryStatusGuid());
        deficiencyDetail.setEntryStatus(entryStatus);
        deficiencyDetail.setDueDate(deficiencyDetailsRequestDto.getDueDate());

        deficiencyDetailsRepository.save(deficiencyDetail);

        return new ResponseEntity(HttpStatus.OK);
    }

    public EntryResponseDto _getEntryDetails(Object[] entryObj){
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
        //entries.add(entryResponseDto);

        if(entryTypeGuid.equals("1")){//in case of deficiency
            DeficiencyDetailsEntity deficiencyDetails = entry.getDeficiencyDetails();

            if(deficiencyDetails != null){
                List<Object[]> deficiencyDetailsObjList = deficiencyDetailsRepository.getDeficiencyDetails(deficiencyDetails.getDeficiencyDetailsGuid(),  LocaleContextHolder.getLocale().getDisplayLanguage());
                Object[] deficiencyDetailsObj;
                if(deficiencyDetailsObjList !=null && deficiencyDetailsObjList.size() > 0){
                    deficiencyDetailsObj = deficiencyDetailsObjList.get(0);

                    DeficiencyDetailsResponseDto deficiencyDetailsResponseDto = new DeficiencyDetailsResponseDto();

                    deficiencyDetailsResponseDto.setDeficiencyDetailsGuid((String) deficiencyDetailsObj[0]);
                    deficiencyDetailsResponseDto.setDueDate((Date) deficiencyDetailsObj[1]);
                    deficiencyDetailsResponseDto.setEntryStatusGuid((String) deficiencyDetailsObj[2]);
                    deficiencyDetailsResponseDto.setEntryStatusName((String) deficiencyDetailsObj[3]);
                    deficiencyDetailsResponseDto.setEntryStatusBackgroundColor((String) deficiencyDetailsObj[4]);

                    entryResponseDto.setDeficiencyDetails(deficiencyDetailsResponseDto);
                }
            }
        }

        if(entryTypeGuid.equals("2")){//in case of deficiency
            ContactDetailsEntity contactDetails = entry.getContactDetails();

            if(contactDetails != null){
                List<Object[]> contactDetailsObjList = contactDetailsRepository.getContactDetails(contactDetails.getContactDetailsGuid(),  LocaleContextHolder.getLocale().getDisplayLanguage());
                Object[] contactDetailsObj;
                if(contactDetailsObjList !=null && contactDetailsObjList.size() > 0){
                    contactDetailsObj = contactDetailsObjList.get(0);

                    ContactDetailsResponseDto contactDetailsResponseDto = new ContactDetailsResponseDto();

                    contactDetailsResponseDto.setContactDetailsGuid((String) contactDetailsObj[0]);
                    contactDetailsResponseDto.setContactTypeGuid((String) contactDetailsObj[1]);
                    contactDetailsResponseDto.setPhotoS3ObjectKey((String) contactDetailsObj[2]);
                    contactDetailsResponseDto.setPersonFirstName((String) contactDetailsObj[3]);
                    contactDetailsResponseDto.setPersonLastName((String) contactDetailsObj[4]);
                    contactDetailsResponseDto.setPersonMobilePhone((String) contactDetailsObj[5]);
                    contactDetailsResponseDto.setPersonEmail((String) contactDetailsObj[6]);
                    contactDetailsResponseDto.setPersonAddress((String) contactDetailsObj[7]);

                    contactDetailsResponseDto.setOrganizationName((String) contactDetailsObj[8]);
                    contactDetailsResponseDto.setOrganizationWebSite((String) contactDetailsObj[9]);
                    contactDetailsResponseDto.setOrganizationContactPhone((String) contactDetailsObj[10]);
                    contactDetailsResponseDto.setOrganizationContactEmail((String) contactDetailsObj[11]);
                    contactDetailsResponseDto.setOrganizationAddress((String) contactDetailsObj[12]);

                    entryResponseDto.setContactDetails(contactDetailsResponseDto);
                }
            }
        }

        return entryResponseDto;
    }

    @RequestMapping(value = "/getEntries", method = RequestMethod.GET)
    @Transactional
    public Page<EntryResponseDto> getEntries(@RequestParam String projectGuid, @RequestParam String entryTypeGuid, @RequestParam String description, Pageable pageable) {
        //auth check if user has admin role for the project
        UserEntity user = SecurityContextReader.getUserEntity(userRepository);//userRepository.findByUsername(securityContextReader.getUsername());
        String[] requiredRoles = {"manager", "user", "viewer"};
        if(!permissionsValidator.rolesForProjectCheck(user, projectGuid, requiredRoles)){
            throw new RuntimeException(bundleMessageReader.getMessage("PermissionsRelatedIssue"));
        }

        List<EntryResponseDto> entries = new ArrayList<EntryResponseDto>();
        Page<Object[]> entriesObj = null;

        String[] managerRole = {"manager"};
        if(permissionsValidator.rolesForProjectCheck(user, projectGuid, managerRole)) {
            if(entryTypeGuid.equals("1")){
               entriesObj = entryRepository.getDeficienciesForProject(projectGuid, '%' + description + '%', LocaleContextHolder.getLocale().getDisplayLanguage(), pageable);
            }
            if(entryTypeGuid.equals("2")){
                entriesObj = entryRepository.getContactsForProject(projectGuid, '%' + description + '%', LocaleContextHolder.getLocale().getDisplayLanguage(), pageable);
            }

        } else {

            List<String> groups = new ArrayList<String>();
            for (GroupEntity group: user.getGroups()) {
                if(group.getProject().getProjectGuid().equals(projectGuid)){
                    groups.add(group.getGroupGuid());
                }
            }

            if(groups.size() > 0){
                if(entryTypeGuid.equals("1")) {
                    entriesObj = entryRepository.getDeficienciesForProjectAndGroups(projectGuid, '%' + description + '%', groups, LocaleContextHolder.getLocale().getDisplayLanguage(), pageable);
                }

                if(entryTypeGuid.equals("2")) {
                    entriesObj = entryRepository.getContactsForProjectAndGroups(projectGuid, '%' + description + '%', groups, LocaleContextHolder.getLocale().getDisplayLanguage(), pageable);
                }
            }else{
                return null;
            }
        }

        Long entiesNumber = entriesObj.getTotalElements();

        for (Object[] entryObj : entriesObj.getContent()) {

            EntryResponseDto entryResponseDto = this._getEntryDetails(entryObj);
            entries.add(entryResponseDto);
        }

        Page<EntryResponseDto> page = new PageImpl<>(entries, pageable, entiesNumber);
        return page;
    }

    @RequestMapping(value = "/getEntry/{entryGuid}", method = RequestMethod.GET)
    @Transactional
    public EntryResponseDto getEntry(@PathVariable String entryGuid) {
        EntryEntity entry = entryRepository.findByEntryGuid(entryGuid);
        String projectGuid = entry.getProject().getProjectGuid();
        //auth check if user has manager role for the project
        UserEntity user = SecurityContextReader.getUserEntity(userRepository);//userRepository.findByUsername(securityContextReader.getUsername());
        String[] requiredRoles = {"manager", "user", "viewer"};
        if(!permissionsValidator.rolesForProjectCheck(user, projectGuid, requiredRoles)){
            throw new RuntimeException(bundleMessageReader.getMessage("PermissionsRelatedIssue"));
        }

        ArrayList<Object[]> entryObj;
        String[] managerRole = {"manager"};
        if(!permissionsValidator.rolesForProjectCheck(user, entry.getProject().getProjectGuid(), requiredRoles)){
            List<String> groups = new ArrayList<String>();
            for (GroupEntity group: user.getGroups()) {
                if(group.getProject().getProjectGuid().equals(projectGuid)){
                    groups.add(group.getGroupGuid());
                }
            }

            if(groups.size() > 0){
                entryObj = entryRepository.getEntryByGuidForGroups(entryGuid, groups, LocaleContextHolder.getLocale().getDisplayLanguage());
            }else{
                return null;
            }
        }else{
            entryObj = entryRepository.getEntryByGuid(entryGuid, LocaleContextHolder.getLocale().getDisplayLanguage());
        }

        EntryResponseDto entryResponseDto = new EntryResponseDto();

        if(entryObj != null){
            entryResponseDto = this._getEntryDetails(entryObj.get(0));
        }

        return entryResponseDto;
    }

    @RequestMapping(value = "/getContactTypes", method = RequestMethod.GET)
    @Transactional
    public List<ContactTypeResponseDto> getContactTypes() {

        List<ContactTypeResponseDto> contactTypeResponseDtos = new ArrayList<ContactTypeResponseDto>();

        ArrayList<Object[]>  contactTypesObj = contactTypeRepository.getContactTypes(LocaleContextHolder.getLocale().getDisplayLanguage());

        for (Object[] contactTypeObj : contactTypesObj) {
            ContactTypeResponseDto contactTypeResponseDto = new ContactTypeResponseDto();
            contactTypeResponseDto.setContactTypeGuid((String) contactTypeObj[0]);
            contactTypeResponseDto.setRanking((Integer) contactTypeObj[1]);
            contactTypeResponseDto.setName((String) contactTypeObj[2]);

            contactTypeResponseDtos.add(contactTypeResponseDto);
        }

        return contactTypeResponseDtos;
    }

    @RequestMapping(value = "/createContactDetails", method = RequestMethod.POST)
    @Transactional
    public ContactDetailsResponseDto createContactDetails(@Valid @RequestBody ContactDetailsRequestDto contactDetailsRequestDto) {
        EntryEntity entry = entryRepository.findByEntryGuid(contactDetailsRequestDto.getParentEntryGuid());
        //auth check if user has manager role for the project
        UserEntity user = SecurityContextReader.getUserEntity(userRepository);//userRepository.findByUsername(securityContextReader.getUsername());
        String[] requiredRoles = {"manager"};
        if(!permissionsValidator.rolesForProjectCheck(user, entry.getProject().getProjectGuid(), requiredRoles)){
            throw new RuntimeException(bundleMessageReader.getMessage("PermissionsRelatedIssue"));
        }

        ContactDetailsEntity contactDetails = new ContactDetailsEntity();

        ContactTypeEntity contactType = contactTypeRepository.findByContactTypeGuid(contactDetailsRequestDto.getContactTypeGuid());
        contactDetails.setContactType(contactType);

        contactDetails.setPhotoS3ObjectKey(contactDetailsRequestDto.getPhotoS3ObjectKey());
        contactDetails.setPersonFirstName(contactDetailsRequestDto.getPersonFirstName());
        contactDetails.setPersonLastName(contactDetailsRequestDto.getPersonLastName());
        contactDetails.setPersonMobilePhone(contactDetailsRequestDto.getPersonMobilePhone());
        contactDetails.setPersonEmail(contactDetailsRequestDto.getPersonEmail());
        contactDetails.setPersonAddress(contactDetailsRequestDto.getPersonAddress());

        contactDetails.setOrganizationName(contactDetailsRequestDto.getOrganizationName());
        contactDetails.setOrganizationWebSite(contactDetailsRequestDto.getOrganizationWebSite());
        contactDetails.setOrganizationContactPhone(contactDetailsRequestDto.getOrganizationContactPhone());
        contactDetails.setOrganizationContactEmail(contactDetailsRequestDto.getOrganizationContactEmail());
        contactDetails.setOrganizationAddress(contactDetailsRequestDto.getOrganizationAddress());

        // deficiencyDetailsRepository.save(deficiencyDetails);

        entry.setContactDetails(contactDetails);
        if(contactDetailsRequestDto.getContactTypeGuid().equals("1")){
            String fullName = "";

            if(contactDetails.getPersonLastName() != null){
                fullName += contactDetails.getPersonLastName();
            }
            if(contactDetails.getPersonFirstName() != null){
                fullName += ' ' + contactDetails.getPersonFirstName();
            }
            entry.setDescription(fullName);
        }
        if(contactDetailsRequestDto.getContactTypeGuid().equals("2")){
            entry.setDescription(contactDetails.getOrganizationName());
        }

        entryRepository.save(entry);

        ContactDetailsResponseDto contactDetailsResponseDto = new ContactDetailsResponseDto();
        contactDetailsResponseDto.setContactDetailsGuid(contactDetails.getContactDetailsGuid());

        return contactDetailsResponseDto;
    }

    @RequestMapping(value = "/updateContactDetails", method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity updateContactDetails(@Valid @RequestBody ContactDetailsRequestDto contactDetailsRequestDto) {
        EntryEntity entry = entryRepository.findByEntryGuid(contactDetailsRequestDto.getParentEntryGuid());
        //auth check if user has manager role for the project
        UserEntity user = SecurityContextReader.getUserEntity(userRepository);//userRepository.findByUsername(securityContextReader.getUsername());
        String[] requiredRoles = {"manager", "user"};
        if(!permissionsValidator.rolesForProjectCheck(user, entry.getProject().getProjectGuid(), requiredRoles)){
            throw new RuntimeException(bundleMessageReader.getMessage("PermissionsRelatedIssue"));
        }

        ContactDetailsEntity contactDetails = contactDetailsRepository.findByContactDetailsGuid(contactDetailsRequestDto.getContactDetailsGuid());

        contactDetails.setPhotoS3ObjectKey(contactDetailsRequestDto.getPhotoS3ObjectKey());
        contactDetails.setPersonFirstName(contactDetailsRequestDto.getPersonFirstName());
        contactDetails.setPersonLastName(contactDetailsRequestDto.getPersonLastName());
        contactDetails.setPersonMobilePhone(contactDetailsRequestDto.getPersonMobilePhone());
        contactDetails.setPersonEmail(contactDetailsRequestDto.getPersonEmail());
        contactDetails.setPersonAddress(contactDetailsRequestDto.getPersonAddress());

        contactDetails.setOrganizationName(contactDetailsRequestDto.getOrganizationName());
        contactDetails.setOrganizationWebSite(contactDetailsRequestDto.getOrganizationWebSite());
        contactDetails.setOrganizationContactPhone(contactDetailsRequestDto.getOrganizationContactPhone());
        contactDetails.setOrganizationContactEmail(contactDetailsRequestDto.getOrganizationContactEmail());
        contactDetails.setOrganizationAddress(contactDetailsRequestDto.getOrganizationAddress());


        contactDetailsRepository.save(contactDetails);

        if(contactDetailsRequestDto.getContactTypeGuid().equals("1")){
            String fullName = "";

            if(contactDetails.getPersonLastName() != null){
                fullName += contactDetails.getPersonLastName();
            }
            if(contactDetails.getPersonFirstName() != null){
                fullName += ' ' + contactDetails.getPersonFirstName();
            }
            entry.setDescription(fullName);
        }
        if(contactDetailsRequestDto.getContactTypeGuid().equals("2")){
            entry.setDescription(contactDetails.getOrganizationName());
        }
        entryRepository.save(entry);

        return new ResponseEntity(HttpStatus.OK);
    }
}