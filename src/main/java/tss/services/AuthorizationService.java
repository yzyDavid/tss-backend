package tss.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tss.entities.AuthorityEntity;
import tss.entities.RoleEntity;
import tss.entities.TypeGroupEntity;
import tss.entities.UserEntity;
import tss.repositories.AuthorityRepository;

import java.util.Optional;
import java.util.Set;

@Service
public class AuthorizationService {
    @Autowired
    AuthorityRepository authorityRepository;
    @Autowired
    QueryService queryService;

    private AuthorityEntity addAuthority(String uri) {
        if (authorityRepository.findByUri(uri) != null) {
            return null;
        }
        AuthorityEntity authority = new AuthorityEntity();
        authority.setUri(uri);
        authorityRepository.save(authority);
        return authority;
    }

    public boolean checkMethodAccessAuthority(UserEntity user, String uri) {
        Optional<AuthorityEntity> authority = authorityRepository.findByUri(uri);
        if (!authority.isPresent()) {
            return true;
        } else if (user != null) {
            TypeGroupEntity type = user.getType();
            if (type != null) {
                Set<RoleEntity> roles = type.getRoles();
                for (RoleEntity role : roles) {
                    if (role.getAuthorities().contains(authority.get())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkDataAccessAuthority(UserEntity user, String uri) {
        return false;
    }


}
