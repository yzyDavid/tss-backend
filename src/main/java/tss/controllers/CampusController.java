package tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tss.entities.BuildingEntity;
import tss.entities.CampusEntity;
import tss.exceptions.CampusNotFoundException;
import tss.models.Building;
import tss.models.Campus;
import tss.repositories.BuildingRepository;
import tss.repositories.CampusRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author reeve
 */
@RestController
@RequestMapping("/campuses")
public class CampusController {
    private final CampusRepository campusRepository;
    private final BuildingRepository buildingRepository;

    @Autowired
    public CampusController(CampusRepository campusRepository, BuildingRepository buildingRepository) {
        this.campusRepository = campusRepository;
        this.buildingRepository = buildingRepository;
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public Campus insertCampus(@RequestBody Campus campus) {
        CampusEntity campusEntity = new CampusEntity(campus.getName());
        return new Campus(campusRepository.save(campusEntity));
    }

    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public List<Campus> listCampuses() {
        List<CampusEntity> campusEntities = campusRepository.findAll();
        List<Campus> campuses = new ArrayList<>();
        for (CampusEntity campusEntity :
                campusEntities) {
            campuses.add(new Campus(campusEntity));
        }
        return campuses;
    }

    @GetMapping("/{campusId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Campus getCampus(@PathVariable int campusId) {
        CampusEntity campusEntity = campusRepository.findById(campusId).orElseThrow(CampusNotFoundException::new);
        return new Campus(campusEntity);
    }

    @DeleteMapping("/{campusId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeCampus(@PathVariable int campusId) {
        CampusEntity campusEntity = campusRepository.findById(campusId).orElseThrow(CampusNotFoundException::new);
        campusRepository.delete(campusEntity);
    }

    @PatchMapping("/{campusId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Campus updateCampus(@PathVariable int campusId, @RequestBody Campus campus) {
        CampusEntity campusEntity = campusRepository.findById(campusId).orElseThrow(CampusNotFoundException::new);
        if (campus.getName() != null) {
            campusEntity.setName(campus.getName());
        }
        return new Campus(campusRepository.save(campusEntity));
    }

    @PostMapping("/{campusId}/buildings")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Building insertBuilding(@PathVariable int campusId, @RequestBody Building building) {
        CampusEntity campusEntity = campusRepository.findById(campusId).orElseThrow(CampusNotFoundException::new);

        BuildingEntity buildingEntity = new BuildingEntity(building.getName(), null);
        campusEntity.addBuilding(buildingEntity);
        return new Building(buildingRepository.save(buildingEntity));
    }

    @GetMapping("/{campusId}/buildings")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Building> listBuildings(@PathVariable int campusId) {
        CampusEntity campusEntity = campusRepository.findById(campusId).orElseThrow(CampusNotFoundException::new);

        List<BuildingEntity> buildingEntities = campusEntity.getBuildings();
        List<Building> buildings = new ArrayList<>();
        for (BuildingEntity buildingEntity : buildingEntities) {
            buildings.add(new Building(buildingEntity));
        }
        return buildings;
    }
}
