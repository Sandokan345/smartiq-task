package com.smartiq.smartiqtask.Service;

import com.smartiq.smartiqtask.DTO.AutoGalleryDTO;

import java.util.List;

public interface IAutoGalleryService {

    AutoGalleryDTO save(AutoGalleryDTO autoGalleryDTO);

    AutoGalleryDTO update(AutoGalleryDTO autoGalleryDTO);

    void updateCarAmount(AutoGalleryDTO autoGalleryDTO);

    List<AutoGalleryDTO> findAll();

    AutoGalleryDTO findOne(Long id);

    void delete(Long id);
}
