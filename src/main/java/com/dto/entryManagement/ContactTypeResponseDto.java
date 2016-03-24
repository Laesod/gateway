package com.dto.entryManagement;

/**
 * Created by root on 24/03/16.
 */
public class ContactTypeResponseDto {
    private String contactTypeGuid;
    private String name;

    private Integer ranking;

    public String getContactTypeGuid() {
        return contactTypeGuid;
    }

    public void setContactTypeGuid(String contactTypeGuid) {
        this.contactTypeGuid = contactTypeGuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }
}
