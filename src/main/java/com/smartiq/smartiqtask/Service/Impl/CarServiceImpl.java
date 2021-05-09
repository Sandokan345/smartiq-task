package com.smartiq.smartiqtask.Service.Impl;

import com.smartiq.smartiqtask.Constants.BusinessRule;
import com.smartiq.smartiqtask.DTO.AutoGalleryDTO;
import com.smartiq.smartiqtask.DTO.CarDTO;
import com.smartiq.smartiqtask.ExceptionHandler.BusinessException;
import com.smartiq.smartiqtask.Model.AutoGallery;
import com.smartiq.smartiqtask.Model.Car;
import com.smartiq.smartiqtask.Repository.IAutoGalleryDAO;
import com.smartiq.smartiqtask.Repository.ICarDAO;
import com.smartiq.smartiqtask.Service.IAutoGalleryService;
import com.smartiq.smartiqtask.Service.ICarService;
import com.smartiq.smartiqtask.Util.MappingHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CarServiceImpl implements ICarService {

    private final ICarDAO carDAO;

    private final IAutoGalleryDAO autoGalleryDAO;

    private final IAutoGalleryService autoGalleryService;

    public void validate(CarDTO carDTO){
        // Checking whether the car fields are empty
        if (carDTO.getBrand() == null || carDTO.getModel() == null || carDTO.getPrice() <= 0){
            throw new BusinessException(BusinessRule.CAR_NOT_FOUND.getDescription());
        }
        // Checking the auto gallery id of the car
        if (carDTO.getAutoGalleryId() == null || carDTO.getAutoGalleryId() < 0){
            throw new BusinessException(BusinessRule.AUTO_GALLERY_NOT_FOUND.getDescription());
        }
        // Checking whether the given gallery id is registered in the database.
        Optional<AutoGallery> optionalAutoGallery = autoGalleryDAO.findById(carDTO.getAutoGalleryId());
        if (!optionalAutoGallery.isPresent()){
            throw new BusinessException(BusinessRule.AUTO_GALLERY_NOT_FOUND.getDescription());
        }
    }

    public void validateSave(CarDTO carDTO){
        // Provides not updating with the Post method.
        Optional<Car> optionalCar = carDAO.findById(carDTO.getId());
        if (optionalCar.isPresent()){
            throw new BusinessException(BusinessRule.DUPLICATE_ID.getDescription());
        }
    }

    public void validateUpdate(CarDTO carDTO){
        // Checking whether the id of the vehicle to be updated exists in the database
        Optional<Car> optionalCar = carDAO.findById(carDTO.getId());
        if (!optionalCar.isPresent()){
            throw new BusinessException(BusinessRule.CAR_NOT_FOUND.getDescription());
        }
    }

    @Override
    public CarDTO save(CarDTO carDTO) {
        validate(carDTO);
        if (carDTO.getId() != null){
            validateSave(carDTO);
        }
        Car car = MappingHelper.map(carDTO, Car.class);
        Car result = carDAO.save(car);

        // As cars are added, the number of vehicles in the gallery is increased by one
        Optional<AutoGallery> optionalAutoGallery = autoGalleryDAO.findById(carDTO.getAutoGalleryId());
        optionalAutoGallery.get().setCarAmount(optionalAutoGallery.get().getCarAmount() + 1 );
        AutoGalleryDTO autoGalleryDTO = MappingHelper.map(optionalAutoGallery.get(), AutoGalleryDTO.class);
        autoGalleryService.updateCarAmount(autoGalleryDTO);

        return MappingHelper.map(result, CarDTO.class);
    }

    @Override
    public CarDTO update(CarDTO carDTO) {
        validate(carDTO);
        validateUpdate(carDTO);
        if (carDTO.getId() == null){
            throw new BusinessException(BusinessRule.CAR_NOT_FOUND.getDescription());
        } else {
            Car car = MappingHelper.map(carDTO, Car.class);
            Car result = carDAO.save(car);
            return MappingHelper.map(result, CarDTO.class);
        }
    }

    @Override
    public List<CarDTO> findAll() {
        List<Car> carList = carDAO.findAll();
        return MappingHelper.mapList(carList, CarDTO.class);
    }

    @Override
    public CarDTO findOne(Long id) {
        Optional<Car> optionalCar = carDAO.findById(id);
        if (optionalCar.isPresent()){
            Car car = optionalCar.get();
            return MappingHelper.map(car, CarDTO.class);
        } else {
            throw new BusinessException(BusinessRule.CAR_NOT_FOUND.getDescription());
        }
    }

    @Override
    public AutoGalleryDTO findOneGalleryDetails(Long id) {
        Optional<Car> optionalCar = carDAO.findById(id);
        if (optionalCar.isPresent()){
            AutoGallery autoGallery = optionalCar.get().getAutoGallery();
            return MappingHelper.map(autoGallery, AutoGalleryDTO.class);
        } else {
            throw new BusinessException(BusinessRule.CAR_NOT_FOUND.getDescription());
        }
    }

    @Override
    public void delete(Long id) {
        /*
          Active can be false:
          Car car = getById(id);
          car.setActive(false);
          carDAO.update(car);
         */
        Car car = getById(id);
        carDAO.delete(car);

        // As the car is wiped out, the number of cars in the gallery decreases by one
        Optional<AutoGallery> optionalAutoGallery = autoGalleryDAO.findById(car.getAutoGallery().getId());
        optionalAutoGallery.get().setCarAmount(optionalAutoGallery.get().getCarAmount() -1);
        AutoGalleryDTO autoGalleryDTO = MappingHelper.map(optionalAutoGallery.get(), AutoGalleryDTO.class);
        autoGalleryService.updateCarAmount(autoGalleryDTO);
    }

    private Car getById(Long id){
        Optional<Car> optionalCar = carDAO.findById(id);
        if (optionalCar.isPresent()){
            return optionalCar.get();
        } else {
            throw new BusinessException(BusinessRule.CAR_NOT_FOUND.getDescription());
        }
    }
}
