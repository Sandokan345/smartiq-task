package com.smartiq.smartiqtask.Model;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * Created by Ahmet Yakup ÇETİNKAYA on 07.05.2021
 */
@Entity
@Data
public class Car extends BaseEntity{

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private double price;

    // Many vehicles can be in a single gallery. But a vehicle cannot be in more than one gallery.
    // That's why ManyToOne relationship has been established
    // When a gallery is deleted, the vehicles belonging to that gallery are also deleted.
    @ManyToOne
    @JoinColumn(name = "AUTO_GALLERY_ID",  nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AutoGallery autoGallery;
}

