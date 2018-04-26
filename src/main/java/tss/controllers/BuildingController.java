package tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tss.entities.BuildingEntity;
import tss.entities.ClassroomEntity;
import tss.exceptions.BuildingNotFoundException;
import tss.models.Building;
import tss.models.Classroom;
import tss.repositories.BuildingRepository;
import tss.repositories.ClassroomRepository;

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
    private final ClassroomRepository classroomRepository;

    @Autowired
    public BuildingController(BuildingRepository buildingRepository, ClassroomRepository classroomRepository) {
        this.buildingRepository = buildingRepository;
        this.classroomRepository = classroomRepository;
    }

    @GetMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public Building getBuilding(@PathVariable int buildingId) {
        Optional<BuildingEntity> optional = buildingRepository.findById(buildingId);
        if (!optional.isPresent()) {
            throw new BuildingNotFoundException();
        }
        return new Building(optional.get());
    }

    @DeleteMapping()
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeBuilding(@PathVariable int buildingId) {
        Optional<BuildingEntity> optional = buildingRepository.findById(buildingId);
        if (!optional.isPresent()) {
            throw new BuildingNotFoundException();
        }
        buildingRepository.delete(optional.get());
    }

    @PatchMapping()
    @ResponseStatus(value = HttpStatus.OK)
    public Building updateBuilding(@PathVariable int buildingId, @RequestBody Building building) {
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

    @PostMapping("/classrooms")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Classroom insertClassroom(@PathVariable int buildingId, @RequestBody Classroom classroom) {
        Optional<BuildingEntity> optional = buildingRepository.findById(buildingId);
        if (!optional.isPresent()) {
            throw new BuildingNotFoundException();
        }
        BuildingEntity buildingEntity = optional.get();

        ClassroomEntity classroomEntity = new ClassroomEntity(null, classroom.getName(), classroom.getCapacity(),
                buildingEntity);
        classroomEntity = classroomRepository.save(classroomEntity);
        return new Classroom(classroomEntity);
    }

    @GetMapping("/classrooms")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Classroom> listClassrooms(@PathVariable int buildingId) {
        Optional<BuildingEntity> optional = buildingRepository.findById(buildingId);
        if (!optional.isPresent()) {
            throw new BuildingNotFoundException();
        }
        BuildingEntity buildingEntity = optional.get();

        List<ClassroomEntity> classroomEntities = buildingEntity.getClassrooms();
        List<Classroom> classrooms = new ArrayList<>();
        for (ClassroomEntity classroomEntity : classroomEntities) {
            classrooms.add(new Classroom(classroomEntity));
        }
        return classrooms;
    }
}
