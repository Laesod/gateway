package com.dto.entryManagement;

/**
 * Created by root on 29/02/16.
 */
public class EntryTypeResponseDto {
    private String entryTypeGuid;
    private String name;

    public String getEntryTypeGuid() {
        return entryTypeGuid;
    }

    public void setEntryTypeGuid(String entryTypeGuid) {
        this.entryTypeGuid = entryTypeGuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
