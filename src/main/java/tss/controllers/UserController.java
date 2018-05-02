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
import tss.entities.RoleEntity;
import tss.entities.UserEntity;
import tss.repositories.DepartmentRepository;
import tss.repositories.RoleRepository;
import tss.repositories.UserRepository;
import tss.requests.information.*;
import tss.responses.information.*;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.utils.SecurityUtils;

import javax.management.relation.Role;
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
    private final RoleRepository roleRepository;

    @Autowired
    public UserController(UserRepository userRepository, ResourceLoader resourceLoader,
                          DepartmentRepository departmentRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.resourceLoader = resourceLoader;
        this.departmentRepository = departmentRepository;
        this.roleRepository = roleRepository;
    }

    @PutMapping(path = "/add")
    @Authorization
    public ResponseEntity<AddUserResponse> addUser(//@CurrentUser UserEntity user,
                                                   @RequestBody AddUserRequest request) {
        String uid = request.getUid();
        /*if (user.getType() != UserEntity.TYPE_MANAGER) {
            return new ResponseEntity<>(new AddUserResponse("permission denied", "", ""), HttpStatus.FORBIDDEN);
        }*/
        if (userRepository.existsById(uid)) {
            return new ResponseEntity<>(new AddUserResponse("failed with duplicated uid", null, null), HttpStatus.BAD_REQUEST);
        }
        UserEntity entity = new UserEntity();
        entity.setUid(uid);
        entity.setName(request.getName());
        String salt = getSalt();
        String hashedPassword = getHashedPasswordByPasswordAndSalt(request.getPassword(), salt);
        entity.setSalt(salt);
        entity.setHashedPassword(hashedPassword);
        entity.setType(request.getType());

        userRepository.save(entity);
        return new ResponseEntity<>(new AddUserResponse("OK", uid, request.getName()), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/delete")
    @Authorization
    public ResponseEntity<DeleteUserResponse> deleteUser(//@CurrentUser UserEntity user,
                                                         @RequestBody DeleteUserRequest request) {
        String uid = request.getUid();

        /*if (user.getType() != UserEntity.TYPE_MANAGER) {
            return new ResponseEntity<>(new DeleteUserResponse("permission denied", "", ""), HttpStatus.FORBIDDEN);
        }*/
        Optional<UserEntity> ret = userRepository.findById(uid);
        if(!ret.isPresent()) {
            return new ResponseEntity<>(new DeleteUserResponse("non-existent uid", null, null), HttpStatus.BAD_REQUEST);
        }
        UserEntity entity = ret.get();
        String name = entity.getName();
        userRepository.delete(entity);

        return new ResponseEntity<>(new DeleteUserResponse("OK", uid, name), HttpStatus.OK);
    }

    @PostMapping(path = "/modify-pwd")
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

    @PostMapping(path = "/reset-pwd")
    @Authorization
    public ResponseEntity<BasicResponse> resetPwd(@RequestBody BasicUserRequest request) {
        final String initPwd = "123456";
        String uid = request.getUid();
        Optional<UserEntity> ret = userRepository.findById(uid);
        if(!ret.isPresent()) {
            return new ResponseEntity<>(new BasicResponse("non-existent uid"), HttpStatus.BAD_REQUEST);
        }
        UserEntity tar = ret.get();
        String name = tar.getName();
        tar.setHashedPassword(SecurityUtils.getHashedPasswordByPasswordAndSalt(initPwd, tar.getSalt()));
        userRepository.save(tar);

        return new ResponseEntity<>(new BasicResponse("OK"), HttpStatus.OK);
    }

    @PostMapping(path = "/modify")
    @Authorization
    public ResponseEntity<ModifyUserResponse> modifyInfo(@CurrentUser UserEntity user,
                                                         @RequestBody ModifyUserRequest request) {
        if (user.getType() != UserEntity.TYPE_MANAGER && user.getUid() != request.getUid()) {
            return new ResponseEntity<>(new ModifyUserResponse("permission denied", null, null), HttpStatus.FORBIDDEN);
        }
        String uid = (request.getUid() == null) ? user.getUid() : request.getUid();
        Optional<UserEntity> ret = userRepository.findById(uid);
        if(!ret.isPresent()) {
            return new ResponseEntity<>(new ModifyUserResponse("non-existent uid", uid, null), HttpStatus.BAD_REQUEST);
        }
        UserEntity tar = ret.get();
        if (request.getEmail() != null) {
            tar.setEmail(request.getEmail());
        }
        if (request.getTelephone() != null) {
            tar.setTelephone(request.getTelephone());
        }
        if (request.getIntro() != null) {
            tar.setIntro(request.getIntro());
        }
        userRepository.save(tar);
        return new ResponseEntity<>(new ModifyUserResponse("OK", tar.getUid(), tar.getName()), HttpStatus.OK);
    }

    @PostMapping(path = "/modify-own")
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
        return new ResponseEntity<>(new ModifyUserResponse("OK", user.getUid(), user.getName()), HttpStatus.OK);
    }

    @PostMapping(path = "/modify-all")
    @Authorization
    public ResponseEntity<ModifyUserResponse> modifyAllInfo(@RequestBody ModifyUserRequest request) {
        String uid = request.getUid();
        Optional<UserEntity> ret = userRepository.findById(uid);
        if(!ret.isPresent()) {
            return new ResponseEntity<>(new ModifyUserResponse("non-existent uid", uid, null), HttpStatus.BAD_REQUEST);
        }
        UserEntity user = ret.get();
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        if (request.getTelephone() != null) {
            user.setTelephone(request.getTelephone());
        }
        if (request.getIntro() != null) {
            user.setIntro(request.getIntro());
        }

        Optional<DepartmentEntity> dept = departmentRepository.findByName(request.getDeptName());
        if(dept.isPresent()) {
            user.setDepartment(dept.get());
        } else {
            return new ResponseEntity<>(new ModifyUserResponse("No such department", user.getUid(), user.getName()), HttpStatus.OK);
        }

        Optional<RoleEntity> role = roleRepository.findByName(request.getRole());
        if(role.isPresent()) {
            user.getRoles().add(role.get());
        } else {
            return new ResponseEntity<>(new ModifyUserResponse("No such role", user.getUid(), user.getName()), HttpStatus.OK);
        }

        userRepository.save(user);
        return new ResponseEntity<>(new ModifyUserResponse("OK", user.getUid(), user.getName()), HttpStatus.OK);
    }

    @PostMapping(path = "/get")
    @Authorization
    public ResponseEntity<GetUserByUidResponse> getInfo(@CurrentUser UserEntity curUser,
                                                        @RequestBody GetUserByUidRequest request) {
        String uid = (request.getUid() == null) ? curUser.getUid() : request.getUid();
        Optional<UserEntity> ret = userRepository.findById(uid);
        if(!ret.isPresent()) {
            return new ResponseEntity<>(new GetUserByUidResponse("non-existent uid", "", "", -1,
                    "", "", ""), HttpStatus.BAD_REQUEST);
        }
        UserEntity user = ret.get();
        return new ResponseEntity<>(new GetUserByUidResponse("OK", user.getUid(), user.getName(), user.getType(),
                user.getEmail(), user.getTelephone(), user.getIntro()), HttpStatus.OK);
    }

    @PostMapping(path = "/name")
    @Authorization
    public ResponseEntity<GetUsersByNameResponse> getUidsByName(@RequestBody GetUsersByNameRequest request) {
        List<String> uids = new ArrayList<>();
        List<UserEntity> ret = userRepository.findByName(request.getName());
        for(UserEntity user : ret) {
            uids.add(user.getUid());
        }
        return new ResponseEntity<>(new GetUsersByNameResponse("OK", uids), HttpStatus.OK);
    }

    @PostMapping(path = "/modifyPhoto")
    @Authorization
    public ResponseEntity<ModifyPhotoResponse> modifyPhoto(@CurrentUser UserEntity user,
                                                           @RequestBody ModifyPhotoRequest request) {
        /*if (!userRepository.existsById(request.getUid())) {
            return new ResponseEntity<>(new ModifyPhotoResponse("non-existent uid"), HttpStatus.BAD_REQUEST);
        } else if (user.getType() != UserEntity.TYPE_MANAGER && user.getUid() != request.getUid()) {
            return new ResponseEntity<>(new ModifyPhotoResponse("permission denied"), HttpStatus.FORBIDDEN);
        }
        user = userRepository.findById(user.getUid()).get();*/
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

    @PostMapping(path = "/getPhoto")
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
