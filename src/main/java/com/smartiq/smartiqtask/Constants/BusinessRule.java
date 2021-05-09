package com.smartiq.smartiqtask.Constants;

import lombok.Getter;

@Getter
public enum BusinessRule {

    CAR_NOT_FOUND("CAR_NOT_FOUND"),
    AUTO_GALLERY_NOT_FOUND("AUTO_GALLERY_NOT_FOUND"),
    GALLERY_NAME_IN_USE("GALLERY_NAME_IN_USE"),
    GALLERY_ID_CANNOT_BE_CHANGED("GALLERY_ID_CANNOT_BE_CHANGED"),
    DUPLICATE_ID("DUPLICATE_ID");

    BusinessRule(String description) {
        this.description = description;
    }

    private final String description;
}
