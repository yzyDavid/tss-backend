package tss.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tss.entities.AuthorityEntity;
import tss.entities.RoleEntity;
import tss.entities.TypeGroupEntity;
import tss.entities.UserEntity;
import tss.repositories.AuthorityRepository;
import tss.repositories.RoleRepository;
import tss.repositories.UserRepository;

import java.util.Optional;

@Service
public class AuthorizationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;

    @Autowired
    public AuthorizationService(UserRepository userRepository, RoleRepository roleRepository,
                                AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authorityRepository = authorityRepository;
    }

    public boolean methodAccessAuth(String uid, String uri) {
        Optional<UserEntity> user = userRepository.findById(uid);
        return user.isPresent() && methodAccessAuth(user.get(), uri);
    }

    public boolean methodAccessAuth(UserEntity user, String uri) {
        Optional<AuthorityEntity> authority = authorityRepository.findByUri(uri);
        if(user != null && authority.isPresent()) {
            TypeGroupEntity typeGroup = user.getTypeGroup();
            if(typeGroup == null) {
                return false;
            }
            for(RoleEntity role : typeGroup.getRoles()) {
                if(role.getAuthorities().contains(authority.get())) {
                    return true;
                }
            }
        }
        return false;
    }


}
