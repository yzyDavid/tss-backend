package tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tss.configs.Config;
import tss.entities.UserEntity;
import tss.repositories.UserRepository;
import tss.requests.information.*;
import tss.responses.information.*;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
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

    @Autowired
    public UserController(UserRepository userRepository, ResourceLoader resourceLoader) {
        this.userRepository = userRepository;
        this.resourceLoader = resourceLoader;
    }

    @PutMapping(path = "/add")
//    @Authorization
    public ResponseEntity<AddUserResponse> addUser(//@CurrentUser UserEntity user,
                                                   @RequestBody AddUserRequest request) {
        String uid = request.getUid();
//        if (user.getType() != UserEntity.TYPE_MANAGER) {
//            return new ResponseEntity<>(new AddUserResponse("permission denied", "", ""), HttpStatus.FORBIDDEN);
//        }
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
    public ResponseEntity<DeleteUserResponse> deleteUser(@CurrentUser UserEntity user,
                                                         @RequestBody DeleteUserRequest request) {
        String uid = request.getUid();

        if (user.getType() != UserEntity.TYPE_MANAGER) {
            return new ResponseEntity<>(new DeleteUserResponse("permission denied", "", ""), HttpStatus.FORBIDDEN);
        }
        Optional<UserEntity> ret = userRepository.findById(uid);
        if(!ret.isPresent()) {
            return new ResponseEntity<>(new DeleteUserResponse("non-existent uid", null, null), HttpStatus.BAD_REQUEST);
        }
        UserEntity entity = ret.get();
        String name = entity.getName();
        userRepository.delete(entity);

        return new ResponseEntity<>(new DeleteUserResponse("OK", uid, name), HttpStatus.OK);
    }

    @PostMapping(path = "/pwd")
    @Authorization
    public ResponseEntity<ModifyPwdResponse> modifyPwd(@CurrentUser UserEntity user,
                                                       @RequestBody ModifyPwdRequest request) {
        String uid = (request.getUid() == null) ? user.getUid() : request.getUid();
        if(user.getType() != UserEntity.TYPE_MANAGER || user.getUid() != request.getUid()) {
            return new ResponseEntity<>(new ModifyPwdResponse("permission denied", uid, ""), HttpStatus.FORBIDDEN);
        }
        Optional<UserEntity> ret = userRepository.findById(uid);
        if(!ret.isPresent()) {
            return new ResponseEntity<>(new ModifyPwdResponse("non-existent uid", uid, null), HttpStatus.BAD_REQUEST);
        }
        UserEntity tar = ret.get();
        String name = tar.getName();
        if ((tar.getType() != UserEntity.TYPE_MANAGER || uid.equals(user.getUid())) &&
        !tar.getHashedPassword().equals(SecurityUtils.getHashedPasswordByPasswordAndSalt(request.getOldPwd(), tar.getSalt()))) {
            return new ResponseEntity<>(new ModifyPwdResponse("incorrect password", uid, name), HttpStatus.UNAUTHORIZED);
        }
        tar.setHashedPassword(SecurityUtils.getHashedPasswordByPasswordAndSalt(request.getNewPwd(), tar.getSalt()));
        userRepository.save(tar);

        return new ResponseEntity<>(new ModifyPwdResponse("OK", uid, name), HttpStatus.OK);
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
        if (!userRepository.existsById(request.getUid())) {
            return new ResponseEntity<>(new ModifyPhotoResponse("non-existent uid"), HttpStatus.BAD_REQUEST);
        } else if (user.getType() != UserEntity.TYPE_MANAGER && user.getUid() != request.getUid()) {
            return new ResponseEntity<>(new ModifyPhotoResponse("permission denied"), HttpStatus.FORBIDDEN);
        }
        MultipartFile file = request.getFile();
        user = userRepository.findById(user.getUid()).get();
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
