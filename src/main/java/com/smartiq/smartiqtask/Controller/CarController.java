package com.smartiq.smartiqtask.Controller;

import com.smartiq.smartiqtask.Constants.ApiEndpoints;
import com.smartiq.smartiqtask.DTO.AutoGalleryDTO;
import com.smartiq.smartiqtask.DTO.CarDTO;
import com.smartiq.smartiqtask.Service.ICarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = ApiEndpoints.CAR_API_URL)
public class CarController {

    private final ICarService carService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<CarDTO> listCar(){
        return carService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}")
    public CarDTO getCar(@PathVariable Long id){
        return carService.findOne(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = ApiEndpoints.CAR_GALLERY_DETAILS_URL + "/{id}")
    public AutoGalleryDTO getCarGalleryDetails(@PathVariable Long id){
        return carService.findOneGalleryDetails(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CarDTO save(@RequestBody CarDTO carDTO){
        return carService.save(carDTO);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping
    public CarDTO update(@RequestBody CarDTO carDTO){
        return carService.update(carDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        carService.delete(id);
    }
}
