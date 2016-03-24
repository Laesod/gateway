package com.entity.entryManagement;

import com.entity.BaseEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * Created by root on 24/03/16.
 */
@Entity
@Table(name = "contact_details")
@EntityListeners(AuditingEntityListener.class)
@Audited
public class ContactDetailsEntity extends BaseEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "contact_details_guid"   )
    private String contactDetailsGuid;

    @OneToOne
    @JoinColumn(name = "contactTypeGuid")
    private ContactTypeEntity contactType;

    @Column(name = "person_first_name")
    private String personFirstName;

    @Column(name = "person_last_name")
    private String personLastName;

    @Column(name = "person_mobile_phone")
    private String personMobilePhone;

    @Column(name = "person_email")
    private String personEmail;

    @Column(name = "person_address")
    private String personAddress;

    @Column(name = "organization_name")
    private String organizationName;

    @Column(name = "organization_web_site")
    private String organizationWebSite;

    @Column(name = "organization_contact_phone")
    private String organizationContactPhone;

    @Column(name = "organization_contact_email")
    private String organizationContactEmail;

    @Column(name = "organization_address")
    private String organizationAddress;

    public String getContactDetailsGuid() {
        return contactDetailsGuid;
    }

    public void setContactDetailsGuid(String contactDetailsGuid) {
        this.contactDetailsGuid = contactDetailsGuid;
    }

    public ContactTypeEntity getContactType() {
        return contactType;
    }

    public void setContactType(ContactTypeEntity contactType) {
        this.contactType = contactType;
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