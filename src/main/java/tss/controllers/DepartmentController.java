package tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tss.annotations.session.Authorization;
import tss.entities.DepartmentEntity;
import tss.entities.MajorClassEntity;
import tss.entities.MajorEntity;
import tss.entities.UserEntity;
import tss.repositories.*;
import tss.requests.information.*;
import tss.responses.information.*;

import javax.swing.text.html.Option;
import java.util.*;

@Controller
@RequestMapping(path = "/dept")
public class DepartmentController {
    private final DepartmentRepository departmentRepository;
    private final MajorRepository majorRepository;
    private final MajorClassRepository majorClassRepository;
    private final UserRepository userRepository;

    @Autowired
    public DepartmentController(DepartmentRepository departmentRepository, MajorRepository majorRepository,
                                MajorClassRepository majorClassRepository, UserRepository userRepository) {
        this.departmentRepository = departmentRepository;
        this.majorRepository = majorRepository;
        this.majorClassRepository = majorClassRepository;
        this.userRepository = userRepository;
    }

    @PutMapping(path = "/department/add")
    @Authorization
    public ResponseEntity<AddDepartmentResponse> addDepartment(@RequestBody AddDepartmentRequest request) {
        if (request.getName() == null || request.getName().length() == 0) {
            new ResponseEntity<>(new AddDepartmentResponse("Name mustn't be empty", null), HttpStatus.BAD_REQUEST);
        }
        if (departmentRepository.existsByName(request.getName())) {
            return new ResponseEntity<>(new AddDepartmentResponse("Duplicated name", request.getName()), HttpStatus.BAD_REQUEST);
        }
        DepartmentEntity department = new DepartmentEntity();
        if (request.getName().length() == 0) {
            return new ResponseEntity<>(new AddDepartmentResponse("Name mustn't be empty string", null), HttpStatus.BAD_REQUEST);
        }
        department.setName(request.getName());
        departmentRepository.save(department);
        return new ResponseEntity<>(new AddDepartmentResponse("ok", request.getName()), HttpStatus.CREATED);
    }

    @PostMapping(path = "/department/add/user")
    @Authorization
    @Transactional(rollbackFor = {})
    public ResponseEntity<AddUsersToDeptResponse> addUsersToDept(@RequestBody AddUsersToDeptRequest request) {
        Optional<DepartmentEntity> department = departmentRepository.findByName(request.getDepartment());
        if (!department.isPresent()) {
            return new ResponseEntity<>(new AddUsersToDeptResponse("Non-exist department", request.getDepartment(), null), HttpStatus.BAD_REQUEST);
        }
        DepartmentEntity dept = department.get();
        List<UserEntity> users = new ArrayList<>();
        for (String uid : request.getUids()) {
            Optional<UserEntity> user = userRepository.findById(uid);
            if (!user.isPresent()) {
                return new ResponseEntity<>(new AddUsersToDeptResponse("Non-exist user", null, uid), HttpStatus.BAD_REQUEST);
            }
            if (user.get().getMajorClass() == null) {
                users.add(user.get());
            }
        }
        for (UserEntity user : users) {
            user.setDepartment(dept);
            userRepository.save(user);
        }
        return new ResponseEntity<>(new AddUsersToDeptResponse("Ok", request.getDepartment(), null), HttpStatus.OK);
    }

    @PostMapping(path = "/department/delete/user")
    @Authorization
    @Transactional(rollbackFor = {})
    public ResponseEntity<AddUsersToDeptResponse> deleteUsersFromDept(@RequestBody AddUsersToDeptRequest request) {
        String deptName = request.getDepartment();
        if (!departmentRepository.existsByName(deptName)) {
            return new ResponseEntity<>(new AddUsersToDeptResponse("Non-exist department", deptName, null), HttpStatus.BAD_REQUEST);
        }
        List<UserEntity> users = new ArrayList<>();
        for (String uid : request.getUids()) {
            Optional<UserEntity> user = userRepository.findById(uid);
            if (!user.isPresent()) {
                return new ResponseEntity<>(new AddUsersToDeptResponse("Non-exist user", null, uid), HttpStatus.BAD_REQUEST);
            } else {
                DepartmentEntity department = user.get().getDepartment();
                if (department == null || !department.getName().equals(deptName)) {
                    return new ResponseEntity<>(new AddUsersToDeptResponse("User doesn't belong to this department", deptName, uid), HttpStatus.BAD_REQUEST);
                } else if (user.get().getMajorClass() == null) {
                    users.add(user.get());
                }
            }
        }
        for (UserEntity user : users) {
            user.setDepartment(null);
            userRepository.save(user);
        }
        return new ResponseEntity<>(new AddUsersToDeptResponse("Ok", request.getDepartment(), null), HttpStatus.OK);
    }

    @DeleteMapping(path = "/department/delete")
    @Authorization
    public ResponseEntity<DeleteDepartmentResponse> deleteDepartment(@RequestBody DeleteDepartmentRequest request) {
        Optional<DepartmentEntity> department = departmentRepository.findByName(request.getName());
        if (!department.isPresent()) {
            return new ResponseEntity<>(new DeleteDepartmentResponse("Non-exist department", request.getName()), HttpStatus.BAD_REQUEST);
        }
        departmentRepository.delete(department.get());
        return new ResponseEntity<>(new DeleteDepartmentResponse("ok", request.getName()), HttpStatus.OK);
    }

    @PostMapping(path = "/department/get/list")
    @Authorization
    public ResponseEntity<GetDepartmentsResponse> getDepartments() {
        Iterable<DepartmentEntity> departments = departmentRepository.findAll();
        List<String> names = new ArrayList<>();
        for (DepartmentEntity dept : departments) {
            names.add(dept.getName());
        }
        return new ResponseEntity<>(new GetDepartmentsResponse("ok", names), HttpStatus.OK);
    }

    @PostMapping(path = "/department/get/info")
    @Authorization
    @Transactional(rollbackFor = {})
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

    @PostMapping(path = "/department/modify")
    @Authorization
    public ResponseEntity<ModifyDepartmentResponse> modifyDepartment(@RequestBody ModifyDepartmentRequest request) {
        Optional<DepartmentEntity> ret = departmentRepository.findByName(request.getName());
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new ModifyDepartmentResponse("Non-exist department", request.getName()), HttpStatus.BAD_REQUEST);
        }
        DepartmentEntity department = ret.get();
        if (request.getNewName() != null) {
            if (request.getNewName().length() == 0) {
                return new ResponseEntity<>(new ModifyDepartmentResponse("Name mustn't be empty", null), HttpStatus.BAD_REQUEST);

            }
            department.setName(request.getNewName());
            departmentRepository.save(department);
        }
        return new ResponseEntity<>(new ModifyDepartmentResponse("ok", department.getName()), HttpStatus.OK);
    }


    @PutMapping(path = "/major/add")
    @Authorization
    public ResponseEntity<AddMajorResponse> addMajor(@RequestBody AddMajorRequest request) {
        if (request.getName() == null || request.getName().length() == 0) {
            return new ResponseEntity<>(new AddMajorResponse("Name mustn't be empty string", null, null), HttpStatus.BAD_REQUEST);
        }
        if (majorRepository.existsByName(request.getName())) {
            return new ResponseEntity<>(new AddMajorResponse("Duplicated name", request.getName(), null), HttpStatus.BAD_REQUEST);
        }
        MajorEntity major = new MajorEntity();
        major.setName(request.getName());
        Optional<DepartmentEntity> department = departmentRepository.findByName(request.getDepartment());
        if (!department.isPresent()) {
            return new ResponseEntity<>(new AddMajorResponse("Non-exist department", null, request.getDepartment()), HttpStatus.BAD_REQUEST);
        }
        major.setDepartment(department.get());
        majorRepository.save(major);
        return new ResponseEntity<>(new AddMajorResponse("ok", request.getName(), request.getDepartment()), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/major/delete")
    @Authorization
    public ResponseEntity<DeleteMajorResponse> deleteMajor(@RequestBody DeleteMajorRequest request) {
        Optional<MajorEntity> major = majorRepository.findByName(request.getName());
        if (!major.isPresent()) {
            return new ResponseEntity<>(new DeleteMajorResponse("Non-exist major", request.getName()), HttpStatus.BAD_REQUEST);
        }
        majorRepository.delete(major.get());
        return new ResponseEntity<>(new DeleteMajorResponse("ok", request.getName()), HttpStatus.OK);
    }

    @PostMapping(path = "/major/get/info")
    @Authorization
    @Transactional(rollbackFor = {})
    public ResponseEntity<GetMajorResponse> getMajor(@RequestBody GetMajorRequest request) {
        Optional<MajorEntity> ret = majorRepository.findByName(request.getName());
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new GetMajorResponse("Non-exist department", null, null, null), HttpStatus.BAD_REQUEST);
        }
        MajorEntity major = ret.get();
        List<String> classes = new ArrayList<>();
        for (MajorClassEntity majorClass : major.getClasses()) {
            classes.add(majorClass.getName());
        }
        return new ResponseEntity<>(new GetMajorResponse("ok", major.getName(), major.getDepartment().getName(), classes), HttpStatus.OK);
    }

    @PostMapping(path = "/major/modify")
    @Authorization
    public ResponseEntity<ModifyMajorResponse> modifyMajor(@RequestBody ModifyMajorRequest request) {
        Optional<MajorEntity> ret = majorRepository.findByName(request.getName());
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new ModifyMajorResponse("Non-exist major", request.getName(), null, null), HttpStatus.BAD_REQUEST);
        }
        MajorEntity major = ret.get();
        if (request.getNewName() != null) {
            if (request.getNewName().length() == 0) {
                return new ResponseEntity<>(new ModifyMajorResponse("Name mustn't be empty string", null, null, null), HttpStatus.BAD_REQUEST);
            }
            major.setName(request.getNewName());
        }
        if (request.getDepartment() != null) {
            Optional<DepartmentEntity> department = departmentRepository.findByName(request.getDepartment());
            if (!department.isPresent()) {
                return new ResponseEntity<>(new ModifyMajorResponse("Non-exist department", null, null, request.getDepartment()), HttpStatus.BAD_REQUEST);
            }
            major.setDepartment(department.get());
        }
        majorRepository.save(major);
        return new ResponseEntity<>(new ModifyMajorResponse("ok", null, major.getName(), major.getDepartment().getName()), HttpStatus.OK);
    }


    @PutMapping(path = "/class/add")
    @Authorization
    public ResponseEntity<AddMajorClassResponse> addMajorClass(@RequestBody AddMajorClassRequest request) {
        if (request.getName().length() == 0) {
            return new ResponseEntity<>(new AddMajorClassResponse("Name mustn't be empty string", null, null), HttpStatus.BAD_REQUEST);
        }
        if (majorClassRepository.existsByName(request.getName())) {
            return new ResponseEntity<>(new AddMajorClassResponse("Duplicated name", request.getName(), null), HttpStatus.BAD_REQUEST);
        }
        MajorClassEntity majorClass = new MajorClassEntity();
        majorClass.setName(request.getName());
        Optional<MajorEntity> major = majorRepository.findByName(request.getMajor());
        if (!major.isPresent()) {
            return new ResponseEntity<>(new AddMajorClassResponse("Non-exist major", null, request.getMajor()), HttpStatus.BAD_REQUEST);
        }
        majorClass.setMajor(major.get());
        majorClass.setYear(Calendar.getInstance().get(Calendar.YEAR));
        majorClassRepository.save(majorClass);
        return new ResponseEntity<>(new AddMajorClassResponse("ok", request.getName(), request.getMajor()), HttpStatus.CREATED);
    }

    @PostMapping(path = "/class/add/user")
    @Authorization
    @Transactional(rollbackFor = {})
    public ResponseEntity<AddUsersToMajorClassResponse> addUsersToMajorClass(@RequestBody AddUsersToMajorClassRequest request) {
        Optional<MajorClassEntity> majorClass = majorClassRepository.findByName(request.getMajorClass());
        if (!majorClass.isPresent()) {
            return new ResponseEntity<>(new AddUsersToMajorClassResponse("Non-exist class", request.getMajorClass(), null), HttpStatus.BAD_REQUEST);
        }
        List<UserEntity> users = new ArrayList<>();
        for (String uid : request.getUids()) {
            Optional<UserEntity> user = userRepository.findById(uid);
            if (!user.isPresent()) {
                return new ResponseEntity<>(new AddUsersToMajorClassResponse("Non-exist user", null, uid), HttpStatus.BAD_REQUEST);
            } else {
                users.add(user.get());
            }
        }
        DepartmentEntity department = majorClass.get().getMajor().getDepartment();
        for (UserEntity user : users) {
            user.setMajorClass(majorClass.get());
            user.setDepartment(department);
            userRepository.save(user);
        }
        return new ResponseEntity<>(new AddUsersToMajorClassResponse("Ok", request.getMajorClass(), null), HttpStatus.OK);
    }

    @PostMapping(path = "/class/delete/user")
    @Authorization
    @Transactional(rollbackFor = {})
    public ResponseEntity<AddUsersToMajorClassResponse> deleteUsersFromMajorClass(@RequestBody AddUsersToMajorClassRequest request) {
        Optional<MajorClassEntity> majorClass = majorClassRepository.findByName(request.getMajorClass());
        if (!majorClass.isPresent()) {
            return new ResponseEntity<>(new AddUsersToMajorClassResponse("Non-exist class", request.getMajorClass(), null), HttpStatus.BAD_REQUEST);
        }
        List<UserEntity> users = new ArrayList<>();
        for (String uid : request.getUids()) {
            Optional<UserEntity> user = userRepository.findById(uid);
            if (!user.isPresent()) {
                return new ResponseEntity<>(new AddUsersToMajorClassResponse("Non-exist user", null, uid), HttpStatus.BAD_REQUEST);
            } else {
                MajorClassEntity clazz = user.get().getMajorClass();
                if (clazz == null || !clazz.getName().equals(request.getMajorClass())) {
                    return new ResponseEntity<>(new AddUsersToMajorClassResponse("User doesn't belong to this class", request.getMajorClass(), uid), HttpStatus.BAD_REQUEST);
                } else {
                    users.add(user.get());
                }
            }
        }
        for (UserEntity user : users) {
            user.setMajorClass(null);
            user.setDepartment(null);
            userRepository.save(user);
        }
        return new ResponseEntity<>(new AddUsersToMajorClassResponse("Ok", request.getMajorClass(), null), HttpStatus.OK);
    }

    @DeleteMapping(path = "/class/delete")
    @Authorization
    public ResponseEntity<DeleteMajorClassResponse> deleteMajorClass(@RequestBody DeleteMajorClassRequest request) {
        Optional<MajorClassEntity> majorClass = majorClassRepository.findByName(request.getName());
        if (!majorClass.isPresent()) {
            return new ResponseEntity<>(new DeleteMajorClassResponse("Non-exist class", null), HttpStatus.BAD_REQUEST);
        }
        majorClassRepository.delete(majorClass.get());
        return new ResponseEntity<>(new DeleteMajorClassResponse("ok", request.getName()), HttpStatus.OK);
    }

    @PostMapping(path = "/class/get/info")
    @Authorization
    @Transactional(rollbackFor = {})
    public ResponseEntity<GetMajorClassResponse> getMajorClass(@RequestBody GetMajorClassRequest request) {
        Optional<MajorClassEntity> ret = majorClassRepository.findByName(request.getName());
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new GetMajorClassResponse("Non-exist department", request.getName(), null, null, null, null), HttpStatus.BAD_REQUEST);
        }
        MajorClassEntity majorClass = ret.get();
        List<String> uids = new ArrayList<>();
        List<String> unames = new ArrayList<>();
        for (UserEntity student : majorClass.getStudents()) {
            uids.add(student.getUid());
            unames.add(student.getName());
        }
        return new ResponseEntity<>(new GetMajorClassResponse("ok", majorClass.getName(), majorClass.getMajor().getName(), majorClass.getYear(), uids, unames), HttpStatus.OK);
    }

    @PostMapping(path = "/class/modify")
    @Authorization
    public ResponseEntity<ModifyMajorClassResponse> modifyMajorClass(@RequestBody ModifyMajorClassRequest request) {
        Optional<MajorClassEntity> ret = majorClassRepository.findByName(request.getName());
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new ModifyMajorClassResponse("Non-exist major class", request.getName(), null, null, null), HttpStatus.BAD_REQUEST);
        }
        MajorClassEntity majorClass = ret.get();
        if (request.getNewName() != null) {
            if (request.getNewName().length() == 0) {
                return new ResponseEntity<>(new ModifyMajorClassResponse("Name mustn't be empty string", null, null, null, null), HttpStatus.BAD_REQUEST);
            }
            majorClass.setName(request.getNewName());
        }
        if (request.getMajor() != null) {
            Optional<MajorEntity> major = majorRepository.findByName(request.getMajor());
            if (!major.isPresent()) {
                return new ResponseEntity<>(new ModifyMajorClassResponse("Non-exist major", null, null, request.getMajor(), null), HttpStatus.BAD_REQUEST);
            }
            majorClass.setMajor(major.get());
        }
        if (request.getYear() != null) {
            majorClass.setYear(request.getYear());
        }
        return new ResponseEntity<>(new ModifyMajorClassResponse("ok", request.getName(), majorClass.getName(), majorClass.getMajor().getName(), majorClass.getYear()), HttpStatus.OK);
    }
}