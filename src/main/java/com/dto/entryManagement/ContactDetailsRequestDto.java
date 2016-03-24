package com.dto.entryManagement;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

/**
 * Created by root on 24/03/16.
 */
public class ContactDetailsRequestDto {
    private String contactDetailsGuid;

    @NotNull(message = "{FieldIsMandatory}")
    @NotEmpty(message = "{FieldCanNotBeEmpty}")
    private String parentEntryGuid;

    @NotNull(message = "{FieldIsMandatory}")
    @NotEmpty(message = "{FieldCanNotBeEmpty}")
    private String contactTypeGuid;

    private String personFirstName;

    private String personLastName;

    private String personMobilePhone;

    private String personEmail;

    private String personAddress;

    private String organizationName;

    private String organizationWebSite;

    private String organizationContactPhone;

    private String organizationContactEmail;

    private String organizationAddress;

    public String getContactDetailsGuid() {
        return contactDetailsGuid;
    }

    public void setContactDetailsGuid(String contactDetailsGuid) {
        this.contactDetailsGuid = contactDetailsGuid;
    }

    public String getParentEntryGuid() {
        return parentEntryGuid;
    }

    public void setParentEntryGuid(String parentEntryGuid) {
        this.parentEntryGuid = parentEntryGuid;
    }

    public String getContactTypeGuid() {
        return contactTypeGuid;
    }

    public void setContactTypeGuid(String contactTypeGuid) {
        this.contactTypeGuid = contactTypeGuid;
    }

    public String getPersonFirstName() {
        return personFirstName;
    }

    public void setPersonFirstName(String personFirstName) {
        this.personFirstName = personFirstName;
    }

    public String getPersonLastName() {
        return personLastName;
    }

    public void setPersonLastName(String personLastName) {
        this.personLastName = personLastName;
    }

    public String getPersonMobilePhone() {
        return personMobilePhone;
    }

    public void setPersonMobilePhone(String personMobilePhone) {
        this.personMobilePhone = personMobilePhone;
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }

    public String getPersonAddress() {
        return personAddress;
    }

    public void setPersonAddress(String personAddress) {
        this.personAddress = personAddress;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationWebSite() {
        return organizationWebSite;
    }

    public void setOrganizationWebSite(String organizationWebSite) {
        this.organizationWebSite = organizationWebSite;
    }

    public String getOrganizationContactPhone() {
        return organizationContactPhone;
    }

    public void setOrganizationContactPhone(String organizationContactPhone) {
        this.organizationContactPhone = organizationContactPhone;
    }

    public String getOrganizationContactEmail() {
        return organizationContactEmail;
    }

    public void setOrganizationContactEmail(String organizationContactEmail) {
        this.organizationContactEmail = organizationContactEmail;
    }

    public String getOrganizationAddress() {
        return organizationAddress;
    }

    public void setOrganizationAddress(String organizationAddress) {
        this.organizationAddress = organizationAddress;
    }
}
