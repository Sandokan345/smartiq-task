package com.smartiq.smartiqtask.Repository;

import com.smartiq.smartiqtask.Model.AutoGallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAutoGalleryDAO extends JpaRepository<AutoGallery, Long> {
    Optional<AutoGallery> findByGalleryName(String galleryName);
}
