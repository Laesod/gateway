package com.dto.entryManagement;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by root on 01/03/16.
 */
public class DeficiencyDetailsRequestDto {
    private String deficiencyDetailsGuid;

    @NotNull(message = "{FieldIsMandatory}")
    @NotEmpty(message = "{FieldCanNotBeEmpty}")
    private String parentEntryGuid;

    @NotNull(message = "{FieldIsMandatory}")
    @NotEmpty(message = "{FieldCanNotBeEmpty}")
    private String entryStatusGuid;

    public String getDeficiencyDetailsGuid() {
        return deficiencyDetailsGuid;
    }

    public void setDeficiencyDetailsGuid(String deficiencyDetailsGuid) {
        this.deficiencyDetailsGuid = deficiencyDetailsGuid;
    }

    public String getParentEntryGuid() {
        return parentEntryGuid;
    }

    public void setParentEntryGuid(String parentEntryGuid) {
        this.parentEntryGuid = parentEntryGuid;
    }

    public String getEntryStatusGuid() {
        return entryStatusGuid;
    }

    public void setEntryStatusGuid(String entryStatusGuid) {
        this.entryStatusGuid = entryStatusGuid;
    }
}
