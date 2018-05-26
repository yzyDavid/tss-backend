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
import tss.entities.DepartmentEntity;
import tss.entities.MajorEntity;
import tss.repositories.DepartmentRepository;
import tss.repositories.MajorClassRepository;
import tss.repositories.MajorRepository;
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
    private final MajorRepository majorRepository;
    private final MajorClassRepository majorClassRepository;

    @Autowired
    public DepartmentController(DepartmentRepository departmentRepository, MajorRepository majorRepository,
                                MajorClassRepository majorClassRepository) {
        this.departmentRepository = departmentRepository;
        this.majorRepository = majorRepository;
        this.majorClassRepository = majorClassRepository;
    }

    @PutMapping(path = "/add/dept")
    @Authorization
    public ResponseEntity<AddDepartmentResponse> addDepartment(@RequestBody AddDepartmentRequest request) {
        if (departmentRepository.existsByName(request.getName())) {
            return new ResponseEntity<>(new AddDepartmentResponse("Duplicated name", request.getName()), HttpStatus.BAD_REQUEST);
        }
        DepartmentEntity department = new DepartmentEntity();
        department.setName(request.getName());
        departmentRepository.save(department);
        return new ResponseEntity<>(new AddDepartmentResponse("ok", request.getName()), HttpStatus.CREATED);
    }

    @PutMapping(path = "/delete/dept")
    @Authorization
    public ResponseEntity<DeleteDepartmentResponse> deleteDepartment(@RequestBody DeleteDepartmentRequest request) {
        if (!departmentRepository.existsByName(request.getName())) {
            return new ResponseEntity<>(new DeleteDepartmentResponse("Non-exist department", null), HttpStatus.BAD_REQUEST);
        }
        departmentRepository.deleteByName(request.getName());
        return new ResponseEntity<>(new DeleteDepartmentResponse("ok", request.getName()), HttpStatus.OK);
    }

    @PostMapping(path = "/get/dept/list")
    @Authorization
    public ResponseEntity<GetDepartmentsResponse> getDepartments() {
        Iterable<DepartmentEntity> departments = departmentRepository.findAll();
        List<String> names = new ArrayList<>();
        for (DepartmentEntity dept : departments) {
            names.add(dept.getName());
        }
        return new ResponseEntity<>(new GetDepartmentsResponse("ok", names), HttpStatus.OK);
    }

    @PostMapping(path = "/get/dept/info")
    @Authorization
    public ResponseEntity<GetDepartmentResponse> getDepartment(@RequestBody GetDepartmentRequest request) {
        Optional<DepartmentEntity> ret = departmentRepository.findByName(request.getName());
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new GetDepartmentResponse("Non-exist department", null, null), HttpStatus.BAD_REQUEST);
        }
        DepartmentEntity dept = ret.get();
        List<String> majors = new ArrayList<>();
        for (MajorEntity major : dept.getMajors()) {
            majors.add(major.getName());
        }
        return new ResponseEntity<>(new GetDepartmentResponse("ok", dept.getName(), majors), HttpStatus.OK);
    }

    @PostMapping(path = "/modify")
    @Authorization
    public ResponseEntity<ModifyDepartmentResponse> modifyDepartment(@RequestBody ModifyDepartmentRequest request) {
        Optional<DepartmentEntity> ret = departmentRepository.findByName(request.getName());
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new ModifyDepartmentResponse("Non-exist department", request.getName()), HttpStatus.BAD_REQUEST);
        }
        DepartmentEntity department = ret.get();
        department.setName(request.getNewName());
        departmentRepository.save(department);
        return new ResponseEntity<>(new ModifyDepartmentResponse("ok", request.getNewName()), HttpStatus.OK);
    }
}
