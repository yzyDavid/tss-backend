package tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tss.entities.CampusEntity;
import tss.exceptions.CampusNotFoundException;
import tss.models.Campus;
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

    @Autowired
    public CampusController(CampusRepository campusRepository) {
        this.campusRepository = campusRepository;
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public Campus insertCampus(@RequestBody Campus campus) {
        CampusEntity campusEntity = new CampusEntity(null, campus.getName(), null);
        campusEntity = campusRepository.save(campusEntity);
        return new Campus(campusEntity);
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
    public Campus getCampus(@PathVariable int campusId) {
        Optional<CampusEntity> optional = campusRepository.findById(campusId);
        if (!optional.isPresent()) throw new CampusNotFoundException();
        return new Campus(optional.get());
    }

    @DeleteMapping("/{campusId}")
    public Campus removeCampus(@PathVariable int campusId) {
        Optional<CampusEntity> optional = campusRepository.findById(campusId);
        if (!optional.isPresent()) throw new CampusNotFoundException();
        CampusEntity campusEntity = optional.get();

        Campus campus = new Campus(campusEntity);
        campusRepository.delete(campusEntity);
        return campus;
    }

    @PatchMapping("/{campusId}")
    public Campus updateCampus(@PathVariable int campusId, @RequestBody Campus campus) {
        Optional<CampusEntity> optional = campusRepository.findById(campusId);
        if (!optional.isPresent()) throw new CampusNotFoundException();
        CampusEntity campusEntity = optional.get();

        if (campus.getName() != null) campusEntity.setName(campus.getName());
        campusEntity = campusRepository.save(campusEntity);
        return new Campus(campusEntity);
    }
}
