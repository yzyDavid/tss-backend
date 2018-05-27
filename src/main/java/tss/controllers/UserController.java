package tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tss.configs.Config;
import tss.entities.DepartmentEntity;
import tss.entities.MajorClassEntity;
import tss.entities.TypeGroupEntity;
import tss.entities.UserEntity;
import tss.repositories.DepartmentRepository;
import tss.repositories.MajorClassRepository;
import tss.repositories.TypeGroupRepository;
import tss.repositories.UserRepository;
import tss.requests.information.*;
import tss.responses.information.*;

import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;

import tss.services.QueryService;

import tss.utils.SecurityUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static tss.utils.SecurityUtils.getHashedPasswordByPasswordAndSalt;
import static tss.utils.SecurityUtils.getSalt;

/**
 * @author yzy
 * <p>
 * For management of User register, delete and query, etc.
 * NOT for sessions.
 */
@Controller
@RequestMapping(path = "/user")
public class UserController {
    private final UserRepository userRepository;
    private final ResourceLoader resourceLoader;
    private final DepartmentRepository departmentRepository;
    private final MajorClassRepository majorClassRepository;
    private final TypeGroupRepository typeGroupRepository;
    private final QueryService queryService;

    @Autowired
    public UserController(UserRepository userRepository, ResourceLoader resourceLoader,
                          DepartmentRepository departmentRepository, TypeGroupRepository typeGroupRepository,
                          MajorClassRepository majorClassRepository, QueryService queryService) {
        this.userRepository = userRepository;
        this.resourceLoader = resourceLoader;
        this.departmentRepository = departmentRepository;
        this.typeGroupRepository = typeGroupRepository;
        this.majorClassRepository = majorClassRepository;
        this.queryService = queryService;
    }

    @PutMapping(path = "/add")
    //   @Authorization
    public ResponseEntity<AddUserResponse> addUser(@RequestBody AddUserRequest request) {
        String[] uids = request.getUids();
        String[] names = request.getNames();
        String[] genders = request.getGenders();
        String[] passwords = request.getPasswords();
        String type = request.getType();
        if (uids == null || names == null || genders == null || passwords == null || type == null) {
            return new ResponseEntity<>(new AddUserResponse("Invalid request", null, null, null, null), HttpStatus.BAD_REQUEST);
        } else if (uids.length != names.length || names.length != genders.length || genders.length != passwords.length) {
            return new ResponseEntity<>(new AddUserResponse("User information number not matching", null, null, null, null), HttpStatus.BAD_REQUEST);
        }

        UserEntity[] users = new UserEntity[uids.length];

        for (int i = 0; i < uids.length; i++) {
            UserEntity user = users[i] = new UserEntity();
            user.setUid(uids[i]);
            user.setName(names[i]);
            String salt = getSalt();
            user.setSalt(salt);
            String password = null;
            if (passwords[i] != null) {
                password = passwords[i];
            } else {
                password = Config.INIT_PWD;
            }
            String hashedPassword = getHashedPasswordByPasswordAndSalt(password, salt);
            user.setHashedPassword(hashedPassword);
            if (request.getType() != null) {
                Optional<TypeGroupEntity> typeGroup = typeGroupRepository.findByName(request.getType());
                if (!typeGroup.isPresent()) {
                    return new ResponseEntity<>(new AddUserResponse("No such user type", uids[i], names[i], genders[i], type), HttpStatus.BAD_REQUEST);
                }
                user.setType(typeGroup.get());
            }
        }

        for (int i = 0; i < uids.length; i++) {
            userRepository.save(users[i]);
        }

        return new ResponseEntity<>(new AddUserResponse("OK", null, null, null, null), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/delete")
    @Authorization
    public ResponseEntity<DeleteUserResponse> deleteUser(@RequestBody DeleteUserRequest request) {
        String[] uids = request.getUids();
        if(uids == null) {
            return new ResponseEntity<>(new DeleteUserResponse("Invalid request", null), HttpStatus.BAD_REQUEST);
        }

        List<String> fails = new ArrayList<>();
        for (String uid : uids) {
            if (uid == null) {
                continue;
            }
            Optional<UserEntity> ret = userRepository.findById(uid);
            if (ret.isPresent()) {
                userRepository.delete(ret.get());
            } else {
                fails.add(uid);
            }

        }
        String[] ret = new String[fails.size()];
        for (int i = 0; i < fails.size(); i++) {
            ret[i] = fails.get(i);
        }
        return new ResponseEntity<>(new DeleteUserResponse("OK", ret), HttpStatus.OK);
    }

    @PostMapping(path = "/modify/pwd")
    @Authorization
    public ResponseEntity<ModifyPwdResponse> modifyPwd(@CurrentUser UserEntity user,
                                                       @RequestBody ModifyPwdRequest request) {

        String name = user.getName();
        if (!user.getHashedPassword().equals(SecurityUtils.getHashedPasswordByPasswordAndSalt(request.getOldPwd(), user.getSalt()))) {
            return new ResponseEntity<>(new ModifyPwdResponse("incorrect password", user.getUid(), name), HttpStatus.UNAUTHORIZED);

        }
        user.setHashedPassword(SecurityUtils.getHashedPasswordByPasswordAndSalt(request.getNewPwd(), user.getSalt()));
        userRepository.save(user);

        return new ResponseEntity<>(new ModifyPwdResponse("OK", user.getUid(), name), HttpStatus.OK);
    }

    @PostMapping(path = "/reset/pwd")
    @Authorization
    public ResponseEntity<BasicResponse> resetPwd(@RequestBody BasicUserRequest request) {
        String uid = request.getUid();
        Optional<UserEntity> ret = userRepository.findById(uid);

        if (!ret.isPresent()) {
            return new ResponseEntity<>(new BasicResponse("non-existent uid"), HttpStatus.BAD_REQUEST);
        }
        UserEntity tar = ret.get();
        tar.setHashedPassword(SecurityUtils.getHashedPasswordByPasswordAndSalt(Config.INIT_PWD, tar.getSalt()));

        userRepository.save(tar);

        return new ResponseEntity<>(new BasicResponse("OK"), HttpStatus.OK);
    }

    @PostMapping(path = "/modify/own/info")
    @Authorization
    public ResponseEntity<ModifyUserResponse> modifyOwnInfo(@CurrentUser UserEntity user,
                                                            @RequestBody ModifyUserRequest request) {
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getTelephone() != null) {
            user.setTelephone(request.getTelephone());
        }
        if (request.getIntro() != null) {
            user.setIntro(request.getIntro());
        }
        userRepository.save(user);
        return new ResponseEntity<>(new ModifyUserResponse("OK", user.getUid(), user.getName(), null, null,
                null, null, user.getEmail(), user.getTelephone(), user.getIntro()), HttpStatus.OK);
    }

    @PostMapping(path = "/modify/info")
    @Authorization
    public ResponseEntity<ModifyUserResponse> modifyInfo(@RequestBody ModifyUserRequest request) {
        String uid = request.getUid();
        Optional<UserEntity> ret = userRepository.findById(uid);

        if (!ret.isPresent()) {
            return new ResponseEntity<>(new ModifyUserResponse("Non-existent uid", uid, null, null, null,
                    null, null, null, null, null), HttpStatus.BAD_REQUEST);
        }
        UserEntity user = ret.get();
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getGender() != null) {
            user.setGender(request.getGender());

        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getTelephone() != null) {
            user.setTelephone(request.getTelephone());
        }
        if (request.getIntro() != null) {
            user.setIntro(request.getIntro());
        }

        if (request.getMajorClass() != null) {
            Optional<MajorClassEntity> majorClass = majorClassRepository.findByName(request.getMajorClass());
            if (majorClass.isPresent()) {
                user.setMajorClass(majorClass.get());
            } else {
                return new ResponseEntity<>(new ModifyUserResponse("No such class", user.getUid(), null, null, user.getMajorClass().getName(),
                        null, null, null, null, null), HttpStatus.BAD_REQUEST);
            }

        }

        if (request.getDeptName() != null) {
            Optional<DepartmentEntity> dept = departmentRepository.findByName(request.getDeptName());
            if (dept.isPresent()) {
                user.setDepartment(dept.get());
            } else {
                return new ResponseEntity<>(new ModifyUserResponse("No such department", user.getUid(), null, null, null,
                        null, user.getDepartment().getName(), null, null, null), HttpStatus.BAD_REQUEST);
            }
        }

        if (request.getType() != null) {
            Optional<TypeGroupEntity> typeGroup = typeGroupRepository.findByName(request.getType());
            if (!typeGroup.isPresent()) {
                return new ResponseEntity<>(new ModifyUserResponse("No such user type", user.getUid(), null, null, null,
                        user.getType().getName(), null, null, null, null), HttpStatus.BAD_REQUEST);
            }
            user.setType(typeGroup.get());
        }

        userRepository.save(user);
        return new ResponseEntity<>(new ModifyUserResponse("OK", user.getUid(), user.getName(), user.getGender(), user.readClassName(),
                user.readTypeName(), user.readDepartmentName(), user.getEmail(), user.getTelephone(), user.getIntro()), HttpStatus.OK);
    }

    @PostMapping(path = "/get/own/info")
    @Authorization
    public ResponseEntity<GetUserInfoResponse> getOwnInfo(@CurrentUser UserEntity curUser) {

        return new ResponseEntity<>(new GetUserInfoResponse("OK", curUser.getUid(), curUser.getName(),
                curUser.readTypeName(), curUser.getEmail(), curUser.getTelephone(), curUser.getIntro(),
                curUser.getGender(), curUser.readDepartmentName(), curUser.readClassName()), HttpStatus.OK);
    }

    @PostMapping(path = "/get/info")
    @Authorization
    public ResponseEntity<GetUserInfoResponse> getInfo(@RequestBody GetUserInfoRequest request) {
        String uid = request.getUid();
        Optional<UserEntity> ret = userRepository.findById(uid);

        if (!ret.isPresent()) {
            return new ResponseEntity<>(new GetUserInfoResponse("Non-existent uid", uid, null, null,
                    null, null, null, null, null, null), HttpStatus.BAD_REQUEST);

        }
        UserEntity user = ret.get();
        return new ResponseEntity<>(new GetUserInfoResponse("OK", user.getUid(), user.getName(),
                user.readTypeName(), user.getEmail(), user.getTelephone(), user.getIntro(),
                user.getGender(), user.readDepartmentName(), user.readClassName()), HttpStatus.OK);
    }

    @PostMapping(path = "/query")
    @Authorization
    public ResponseEntity<QueryUsersResponse> queryUsers(@RequestBody QueryUsersRequest request) {
        Short departmentId = null;
        if(request.getDepartment() != null) {
            Optional<DepartmentEntity> dept = departmentRepository.findByName(request.getDepartment());
            if (!dept.isPresent()) {
                return new ResponseEntity<>(new QueryUsersResponse("Non-exist department", null, null, null), HttpStatus.BAD_REQUEST);
            }
            departmentId = dept.get().getId();
        }
        List<UserEntity> ret = queryService.queryUsers(request.getUid(), request.getName(), departmentId);
        List<String> uids = new ArrayList<>();

        List<String> names = new ArrayList<>();
        List<String> depts = new ArrayList<>();
        for (UserEntity user : ret) {

            uids.add(user.getUid());
            names.add(user.getName());
            if(user.getDepartment() != null) {
                depts.add(user.getDepartment().getName());
            }
            else {
                depts.add(null);
            }
        }
        return new ResponseEntity<>(new QueryUsersResponse("OK", uids, names, depts), HttpStatus.OK);
    }

    @PostMapping(path = "/modify/photo")
    @Authorization
    public ResponseEntity<ModifyPhotoResponse> modifyPhoto(@CurrentUser UserEntity user,
                                                           @RequestBody ModifyPhotoRequest request) {
        MultipartFile file = request.getFile();
        if (!file.isEmpty()) {
            try {
                String fName = file.getOriginalFilename();
                Files.copy(file.getInputStream(), Paths.get(Config.PHOTO_ROOT_DIR, fName));
                user.setPhoto(fName);
            } catch (IOException e) {
                e.printStackTrace();
                return new ResponseEntity<>(new ModifyPhotoResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(new ModifyPhotoResponse("empty file"), HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(new ModifyPhotoResponse("OK"), HttpStatus.CREATED);
    }

    @PostMapping(path = "/get/photo")
    @Authorization
    public ResponseEntity<GetPhotoResponse> getPhoto(@CurrentUser UserEntity user,
                                                     @RequestBody GetPhotoRequest request) {
        String uid = (request.getUid() == null) ? user.getUid() : request.getUid();
        Optional<UserEntity> ret = userRepository.findById(uid);
        if (!ret.isPresent()) {
            return new ResponseEntity<>(new GetPhotoResponse("non-existent uid", null), HttpStatus.BAD_REQUEST);
        }
        UserEntity tar = ret.get();
        try {
            return new ResponseEntity<>(new GetPhotoResponse("OK",
                    resourceLoader.getResource("file:" + Paths.get(Config.PHOTO_ROOT_DIR, tar.getPhoto()))), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new GetPhotoResponse(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }

    }


}