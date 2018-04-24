package tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tss.entities.BuildingEntity;
import tss.entities.CampusEntity;
import tss.exceptions.BuildingNotFoundException;
import tss.exceptions.CampusNotFoundException;
import tss.models.Building;
import tss.repositories.BuildingRepository;
import tss.repositories.CampusRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author reeve
 */
@RestController
@RequestMapping("/buildings/{buildingId}")
public class BuildingController {
    private final BuildingRepository buildingRepository;

    @Autowired
    public BuildingController(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public Building getBuilding (@PathVariable int buildingId) {
        Optional<BuildingEntity> optional = buildingRepository.findById(buildingId);
        if (!optional.isPresent()) {
            throw new BuildingNotFoundException();
        }
        return new Building(optional.get());
    }

    @DeleteMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public Building removeBuilding (@PathVariable int buildingId) {
        Optional<BuildingEntity> optional = buildingRepository.findById(buildingId);
        if (!optional.isPresent()) {
            throw new BuildingNotFoundException();
        }
        BuildingEntity buildingEntity = optional.get();

        Building building = new Building(buildingEntity);
        buildingRepository.delete(buildingEntity);
        return building;
    }

    @PatchMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public Building updateBuilding (@PathVariable int buildingId, @RequestBody Building building) {
        Optional<BuildingEntity> optional = buildingRepository.findById(buildingId);
        if (!optional.isPresent()) {
            throw new BuildingNotFoundException();
        }
        BuildingEntity buildingEntity = optional.get();

        if (building.getName() != null) {
            buildingEntity.setName(building.getName());
        }
        buildingEntity = buildingRepository.save(buildingEntity);
        return new Building(buildingEntity);
    }
}
