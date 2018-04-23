package tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tss.entities.CampusEntity;
import tss.repositories.CampusRepository;
import tss.responses.information.Campus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author reeve
 */
@Controller
public class CampusController {
    private final CampusRepository campusRepository;

    @Autowired
    public CampusController(CampusRepository campusRepository) {
        this.campusRepository = campusRepository;
    }

    @PostMapping(path = "/campuses")
    public ResponseEntity<Campus> addCampus(@RequestBody Campus campus) {
        CampusEntity campusEntity = new CampusEntity(null, campus.getName(), null);
        campusEntity = campusRepository.save(campusEntity);
        return new ResponseEntity<>(new Campus(campusEntity), HttpStatus.OK);
    }

    @GetMapping(path = "/campuses")
    public ResponseEntity<List<Campus>> listCampuses() {
        List<CampusEntity> campusEntities = campusRepository.findAll();
        List<Campus> campuses = new ArrayList<>();
        for (CampusEntity campusEntity :
                campusEntities) {
            campuses.add(new Campus(campusEntity));
        }
        return new ResponseEntity<>(campuses, HttpStatus.OK);
    }
}
