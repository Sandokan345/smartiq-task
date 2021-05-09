package com.smartiq.smartiqtask.Service.Impl;

import com.smartiq.smartiqtask.Constants.BusinessRule;
import com.smartiq.smartiqtask.DTO.AutoGalleryDTO;
import com.smartiq.smartiqtask.ExceptionHandler.BusinessException;
import com.smartiq.smartiqtask.Model.AutoGallery;
import com.smartiq.smartiqtask.Repository.IAutoGalleryDAO;
import com.smartiq.smartiqtask.Service.IAutoGalleryService;
import com.smartiq.smartiqtask.Util.MappingHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class AutoGalleryImpl implements IAutoGalleryService {

    private final IAutoGalleryDAO autoGalleryDAO;

    public void validate(AutoGalleryDTO autoGalleryDTO){
        // Checking whether the auto gallery fields are empty
        if (autoGalleryDTO.getGalleryName() == null){
            throw new BusinessException(BusinessRule.AUTO_GALLERY_NOT_FOUND.getDescription());
        }
        // The same name cannot be given because the gallery name is defined as unique.
        Optional<AutoGallery> optionalAutoGallery = autoGalleryDAO.findByGalleryName(autoGalleryDTO.getGalleryName());
        if (optionalAutoGallery.isPresent()){
            throw new BusinessException(BusinessRule.GALLERY_NAME_IN_USE.getDescription());
        }
    }

    public void validateSave(AutoGalleryDTO autoGalleryDTO){
        // Provides not updating with the Post method.
        Optional<AutoGallery> optionalAutoGallery = autoGalleryDAO.findById(autoGalleryDTO.getId());
        if (optionalAutoGallery.isPresent()){
            throw new BusinessException(BusinessRule.DUPLICATE_ID.getDescription());
        }
    }

    public void validateUpdate(AutoGalleryDTO autoGalleryDTO){
        // Checking whether the id of the gallery to be updated exists in the database
        Optional<AutoGallery> optionalAutoGallery = autoGalleryDAO.findById(autoGalleryDTO.getId());
        if (!optionalAutoGallery.isPresent()){
            throw new BusinessException(BusinessRule.AUTO_GALLERY_NOT_FOUND.getDescription());
        }
    }

    @Override
    public AutoGalleryDTO save(AutoGalleryDTO autoGalleryDTO) {
        validate(autoGalleryDTO);
        if (autoGalleryDTO.getId() != null){
            validateSave(autoGalleryDTO);
        }
        autoGalleryDTO.setCarAmount(0);
        AutoGallery autoGallery = MappingHelper.map(autoGalleryDTO, AutoGallery.class);
        AutoGallery result = autoGalleryDAO.save(autoGallery);
        return MappingHelper.map(result, AutoGalleryDTO.class);
    }

    @Override
    public AutoGalleryDTO update(AutoGalleryDTO autoGalleryDTO) {
        validate(autoGalleryDTO);
        if (autoGalleryDTO.getId() == null){
            throw new BusinessException(BusinessRule.AUTO_GALLERY_NOT_FOUND.getDescription());
        } else {
            validateUpdate(autoGalleryDTO);
            // The number of vehicles is assigned to the 'carAmount' variable to be adjusted later so that it does not change.
            Optional<AutoGallery> optionalAutoGallery = autoGalleryDAO.findById(autoGalleryDTO.getId());
            int carAmount = optionalAutoGallery.get().getCarAmount();
            // Before updating, the number of vehicles is put back in place
            autoGalleryDTO.setCarAmount(carAmount);
            AutoGallery autoGallery = MappingHelper.map(autoGalleryDTO, AutoGallery.class);
            autoGalleryDAO.save(autoGallery);
            return autoGalleryDTO;
        }
    }

    @Override
    public void updateCarAmount(AutoGalleryDTO autoGalleryDTO) {
        AutoGallery autoGallery = MappingHelper.map(autoGalleryDTO, AutoGallery.class);
        autoGalleryDAO.save(autoGallery);
    }

    @Override
    public List<AutoGalleryDTO> findAll() {
        List<AutoGallery> autoGalleryList = autoGalleryDAO.findAll();
        return MappingHelper.mapList(autoGalleryList, AutoGalleryDTO.class);
    }

    @Override
    public AutoGalleryDTO findOne(Long id) {
        Optional<AutoGallery> optionalAutoGallery = autoGalleryDAO.findById(id);
        if (optionalAutoGallery.isPresent()){
            AutoGallery autoGallery = optionalAutoGallery.get();
            return MappingHelper.map(autoGallery, AutoGalleryDTO.class);
        } else {
            throw new BusinessException(BusinessRule.AUTO_GALLERY_NOT_FOUND.getDescription());
        }
    }

    @Override
    public void delete(Long id) {
        /*
          Active can be false:
          AutoGallery autoGallery = getById(id);
          autoGallery.setActive(false);
          autoGalleryDAO.update(car);
         */
        AutoGallery autoGallery = getById(id);
        autoGalleryDAO.delete(autoGallery);
    }

    public AutoGallery getById(Long id){
        Optional<AutoGallery> optionalAutoGallery = autoGalleryDAO.findById(id);
        return optionalAutoGallery.orElse(null);
    }
}
