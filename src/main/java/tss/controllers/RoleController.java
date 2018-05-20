package tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tss.annotations.session.Authorization;
import tss.annotations.session.CurrentUser;
import tss.entities.RoleEntity;
import tss.entities.UserEntity;
import tss.repositories.AuthorityRepository;
import tss.repositories.RoleRepository;

@Controller
@RequestMapping(path = "/role")
public class RoleController {
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;

    @Autowired
    public RoleController(RoleRepository roleRepository, AuthorityRepository authorityRepository) {
        this.roleRepository = roleRepository;
        this.authorityRepository = authorityRepository;
    }

    /*@PutMapping(path = "/add")
    @Authorization
    public ResponseEntity<AddRoleResponse> addRole(@CurrentUser UserEntity user,
                                                   @RequestBody AddRoleRequest request) {
        RoleEntity role = new RoleEntity();

    }

    @PutMapping(path = "/delete")
    @Authorization
    public ResponseEntity<DeleteRoleResponse> deleteRole(@CurrentUser UserEntity user,
                                                   @RequestBody DeleteRoleRequest request) {

    }*/


}
