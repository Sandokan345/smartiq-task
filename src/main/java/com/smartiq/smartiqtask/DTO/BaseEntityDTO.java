package com.smartiq.smartiqtask.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntityDTO {

    private Long id;

    private LocalDateTime createDateTime;

    private LocalDateTime updateDateTime;

    // When deleting from the database, instead of deleting, active value is set to 0.
    // But it was used as a representation in this project.
    // Like 'deleted'
    private boolean active = true;
}
