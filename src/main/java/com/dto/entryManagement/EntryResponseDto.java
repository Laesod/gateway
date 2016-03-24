package com.dto.entryManagement;

import com.dto.userManagement.GroupRequestDto;
import com.dto.userManagement.GroupResponseDto;

import java.util.List;

/**
 * Created by root on 01/03/16.
 */
public class EntryResponseDto {
    private String entryGuid;

    private String entryTypeGuid;
    private String entryTypeName;

    private String description;

    private List<GroupResponseDto> groups;

    private DeficiencyDetailsResponseDto deficiencyDetails;

    private ContactDetailsResponseDto contactDetails;

    public String getEntryGuid() {
        return entryGuid;
    }

    public void setEntryGuid(String entryGuid) {
        this.entryGuid = entryGuid;
    }

    public String getEntryTypeGuid() {
        return entryTypeGuid;
    }

    public void setEntryTypeGuid(String entryTypeGuid) {
        this.entryTypeGuid = entryTypeGuid;
    }

    public String getEntryTypeName() {
        return entryTypeName;
    }

    public void setEntryTypeName(String entryTypeName) {
        this.entryTypeName = entryTypeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<GroupResponseDto> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupResponseDto> groups) {
        this.groups = groups;
    }

    public DeficiencyDetailsResponseDto getDeficiencyDetails() {
        return deficiencyDetails;
    }

    public void setDeficiencyDetails(DeficiencyDetailsResponseDto deficiencyDetails) {
        this.deficiencyDetails = deficiencyDetails;
    }

    public ContactDetailsResponseDto getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(ContactDetailsResponseDto contactDetails) {
        this.contactDetails = contactDetails;
    }
}
