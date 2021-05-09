package com.smartiq.smartiqtask.Controller;

import com.smartiq.smartiqtask.Constants.ApiEndpoints;
import com.smartiq.smartiqtask.DTO.AutoGalleryDTO;
import com.smartiq.smartiqtask.Service.IAutoGalleryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = ApiEndpoints.AUTO_GALLERY_API_URL)
public class AutoGalleryController {

    private final IAutoGalleryService autoGalleryService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<AutoGalleryDTO> listAutoGallery(){
        return autoGalleryService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}")
    public AutoGalleryDTO getAutoGallery(@PathVariable Long id){
        return autoGalleryService.findOne(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AutoGalleryDTO save(@RequestBody AutoGalleryDTO autoGalleryDTO){
        return autoGalleryService.save(autoGalleryDTO);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping
    public AutoGalleryDTO update(@RequestBody AutoGalleryDTO autoGalleryDTO){
        return autoGalleryService.update(autoGalleryDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id){
        autoGalleryService.delete(id);
    }
}
