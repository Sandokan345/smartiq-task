package com.smartiq.smartiqtask.Model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
/**
 * Created by Ahmet Yakup ÇETİNKAYA on 07.05.2021
 */
@Entity
@Data
public class AutoGallery extends BaseEntity{

    @Column(unique = true, nullable = false)
    private String galleryName;

    @Column(nullable = false)
    private int carAmount;
}
