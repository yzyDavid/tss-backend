package tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.DepartmentEntity;
import tss.entities.UserEntity;
import tss.repositories.DepartmentRepository;
import tss.requests.information.AddDepartmentRequest;
import tss.requests.information.DeleteDepartmentRequest;
import tss.requests.information.GetDepartmentRequest;
import tss.requests.information.ModifyDepartmentRequest;
import tss.responses.information.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/dept")
public class DepartmentController {
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @PutMapping(path = "/add")
    @Authorization
    public ResponseEntity<AddDepartmentResponse> addDepartment(@CurrentUser UserEntity user,
                                                               @RequestBody AddDepartmentRequest request) {
        if (departmentRepository.existsByName(request.getName())) {
            return new ResponseEntity<>(new AddDepartmentResponse("duplicated name"), HttpStatus.BAD_REQUEST);
        }
        DepartmentEntity department = new DepartmentEntity();
        department.setName(request.getName());
        departmentRepository.save(department);
        return new ResponseEntity<>(new AddDepartmentResponse("ok"), HttpStatus.CREATED);
    }

    @PostMapping(path = "/getAll")
    @Authorization
    public ResponseEntity<GetAllDepartmentsResponse> getAllDepartments() {
        Iterable<DepartmentEntity> departments = departmentRepository.findAll();
        List<String> names = new ArrayList<>();
        List<Short> ids = new ArrayList<>();
        for (DepartmentEntity dept : departments) {
            names.add(dept.getName());
            ids.add(dept.getId());
        }
        return new ResponseEntity<>(new GetAllDepartmentsResponse("ok", ids, names), HttpStatus.OK);
    }

    @PostMapping(path = "/get")
    @Authorization
    public ResponseEntity<GetDepartmentResponse> getDepartment(@RequestBody GetDepartmentRequest request) {
        Optional<DepartmentEntity> ret = null;
        if (request.getDid() != null) {
            ret = departmentRepository.findById(request.getDid());
        } else if (request.getName() != null) {
            ret = departmentRepository.findByName(request.getName());
        }
        if (ret == null || !ret.isPresent()) {
            return new ResponseEntity<>(new GetDepartmentResponse("department doesn't exist", null, null), HttpStatus.BAD_REQUEST);
        }
        DepartmentEntity dept = ret.get();
        return new ResponseEntity<>(new GetDepartmentResponse("ok", dept.getId(), dept.getName()), HttpStatus.OK);
    }

    @PutMapping(path = "/delete")
    @Authorization
    public ResponseEntity<DeleteDepartmentResponse> deleteDepartment(@CurrentUser UserEntity user,
                                                                     @RequestBody DeleteDepartmentRequest request) {
        if (!departmentRepository.existsByName(request.getName())) {
            return new ResponseEntity<>(new DeleteDepartmentResponse("department doesn't exist"), HttpStatus.BAD_REQUEST);
        }
        departmentRepository.deleteByName(request.getName());
        return new ResponseEntity<>(new DeleteDepartmentResponse("ok"), HttpStatus.OK);
    }

    @PostMapping(path = "/modify")
    @Authorization
    public ResponseEntity<ModifyDepartmentResponse> modifyDepartment(@CurrentUser UserEntity user,
                                                                     @RequestBody ModifyDepartmentRequest request) {
        Optional<DepartmentEntity> ret = departmentRepository.findByName(request.getName());
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new ModifyDepartmentResponse("department doesn't exist"), HttpStatus.BAD_REQUEST);
        }
        DepartmentEntity department = ret.get();
        department.setName(request.getNewName());
        departmentRepository.save(department);
        return new ResponseEntity<>(new ModifyDepartmentResponse("ok"), HttpStatus.OK);
    }
}
