package com.dto.entryManagement;

import java.util.Date;

/**
 * Created by root on 02/03/16.
 */
public class DeficiencyDetailsResponseDto {
    private String deficiencyDetailsGuid;
    private String entryStatusGuid;
    private String entryStatusName;
    private String entryStatusBackgroundColor;
    private Date dueDate;

    public String getDeficiencyDetailsGuid() {
        return deficiencyDetailsGuid;
    }

    public void setDeficiencyDetailsGuid(String deficiencyDetailsGuid) {
        this.deficiencyDetailsGuid = deficiencyDetailsGuid;
    }

    public String getEntryStatusGuid() {
        return entryStatusGuid;
    }

    public void setEntryStatusGuid(String entryStatusGuid) {
        this.entryStatusGuid = entryStatusGuid;
    }

    public String getEntryStatusName() {
        return entryStatusName;
    }

    public void setEntryStatusName(String entryStatusName) {
        this.entryStatusName = entryStatusName;
    }

    public String getEntryStatusBackgroundColor() {
        return entryStatusBackgroundColor;
    }

    public void setEntryStatusBackgroundColor(String entryStatusBackgroundColor) {
        this.entryStatusBackgroundColor = entryStatusBackgroundColor;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}
