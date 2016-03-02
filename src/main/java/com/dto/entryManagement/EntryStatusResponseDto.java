package com.dto.entryManagement;

/**
 * Created by root on 01/03/16.
 */
public class EntryStatusResponseDto {
    private String entryStatusGuid;
    private String name;

    private String iconS3ObjectKey;
    private String backgroundColor;
    private Integer ranking;

    public String getEntryStatusGuid() {
        return entryStatusGuid;
    }

    public void setEntryStatusGuid(String entryStatusGuid) {
        this.entryStatusGuid = entryStatusGuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconS3ObjectKey() {
        return iconS3ObjectKey;
    }

    public void setIconS3ObjectKey(String iconS3ObjectKey) {
        this.iconS3ObjectKey = iconS3ObjectKey;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }
}
