package com.smartiq.smartiqtask.DTO;

import lombok.Data;

@Data
public class CarDTO extends BaseEntityDTO{

    private String brand;

    private String model;

    private double price;

    private Long autoGalleryId;
}
