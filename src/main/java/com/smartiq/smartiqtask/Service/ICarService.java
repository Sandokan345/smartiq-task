package com.smartiq.smartiqtask.Service;

import com.smartiq.smartiqtask.DTO.AutoGalleryDTO;
import com.smartiq.smartiqtask.DTO.CarDTO;

import java.util.List;

public interface ICarService {

    CarDTO save(CarDTO carDTO);

    CarDTO update(CarDTO carDTO);

    List<CarDTO> findAll();

    CarDTO findOne(Long id);

    AutoGalleryDTO findOneGalleryDetails(Long id);

    void delete(Long id);
}
