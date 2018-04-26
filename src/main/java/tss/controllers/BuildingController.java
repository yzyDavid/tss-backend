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

/**
 * @author reeve
 */
@RestController
@RequestMapping("/buildings")
public class BuildingController {
    private final BuildingRepository buildingRepository;
    private final ClassroomRepository classroomRepository;

    @Autowired
    public BuildingController(BuildingRepository buildingRepository, ClassroomRepository classroomRepository) {
        this.buildingRepository = buildingRepository;
        this.classroomRepository = classroomRepository;
    }

    @GetMapping("/{buildingId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Building getBuilding(@PathVariable int buildingId) {
        BuildingEntity buildingEntity = buildingRepository.findById(buildingId).orElseThrow
                (BuildingNotFoundException::new);
        return new Building(buildingEntity);
    }

    @DeleteMapping("/{buildingId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void removeBuilding(@PathVariable int buildingId) {
        BuildingEntity buildingEntity = buildingRepository.findById(buildingId).orElseThrow
                (BuildingNotFoundException::new);
        buildingRepository.delete(buildingEntity);
    }

    @PatchMapping("/{buildingId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Building updateBuilding(@PathVariable int buildingId, @RequestBody Building building) {
        BuildingEntity buildingEntity = buildingRepository.findById(buildingId).orElseThrow
                (BuildingNotFoundException::new);
        if (building.getName() != null) {
            buildingEntity.setName(building.getName());
        }
        return new Building(buildingRepository.save(buildingEntity));
    }

    @PostMapping("/{buildingId}/classrooms")
    @ResponseStatus(value = HttpStatus.CREATED)
    public Classroom insertClassroom(@PathVariable int buildingId, @RequestBody Classroom classroom) {
        BuildingEntity buildingEntity = buildingRepository.findById(buildingId).orElseThrow
                (BuildingNotFoundException::new);

        ClassroomEntity classroomEntity = new ClassroomEntity(classroom.getName(), classroom.getCapacity(), null);
        buildingEntity.addClassroom(classroomEntity);
        return new Classroom(classroomRepository.save(classroomEntity));
    }

    @GetMapping("/{buildingId}/classrooms")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Classroom> listClassrooms(@PathVariable int buildingId) {
        BuildingEntity buildingEntity = buildingRepository.findById(buildingId).orElseThrow
                (BuildingNotFoundException::new);

        List<ClassroomEntity> classroomEntities = buildingEntity.getClassrooms();
        List<Classroom> classrooms = new ArrayList<>();
        for (ClassroomEntity classroomEntity : classroomEntities) {
            classrooms.add(new Classroom(classroomEntity));
        }
        return classrooms;
    }
}
