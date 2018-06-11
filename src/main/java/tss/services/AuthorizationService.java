package tss.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tss.configs.Config;
import tss.entities.AuthorityEntity;
import tss.entities.RoleEntity;
import tss.entities.TypeGroupEntity;
import tss.entities.UserEntity;
import tss.repositories.AuthorityRepository;
import tss.repositories.UserRepository;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

@Service
public class AuthorizationService {
    @Autowired
    AuthorityRepository authorityRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    HttpServletRequest request;
    private static Pattern pattern = Pattern.compile("\\{.*}");

    private String getAuthUri(Object entity) {
        if (entity.getClass().getAnnotation(Entity.class) == null) {
            return null;
        }
        String idName;
        for (Field field : entity.getClass().getFields()) {
            if (field.getAnnotation(Id.class) != null) {
                idName = field.getName();
                idName = idName.substring(0,1).toUpperCase()+idName.substring(1, idName.length());
                String uri = null;
                try {
                    Method idGetter = entity.getClass().getMethod("get"+idName);
                    uri = entity.getClass().getName()+"/"+idGetter.invoke(entity).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return uri;
            }
        }
        return null;
    }

    private AuthorityEntity addAuthority4Resource(Object resource) {
        String uri = getAuthUri(resource);
        if (uri == null || authorityRepository.existsByUri(uri)) {
            return null;
        }
        AuthorityEntity authority = new AuthorityEntity();
        authority.setUri(uri);
        authorityRepository.save(authority);
        return authority;
    }

    public boolean authorizeUsers2Resource(Object resource, UserEntity[] users) {
        String uri = getAuthUri(resource);
        if (uri == null) {
            return false;
        }
        Optional<AuthorityEntity> auth = authorityRepository.findByUri(uri);
        if(!auth.isPresent()) {
            return false;
        }
        AuthorityEntity authority = auth.get();
        authorizeUsers2Resource(authority, users);
        return true;
    }

    public void authorizeUsers2Resource(AuthorityEntity authority, UserEntity[] users) {
        for(UserEntity user : users) {
            authority.addUser(user);
        }
        authorityRepository.save(authority);
    }

    @Transactional(rollbackFor = {})
    public boolean checkMethodAccessAuthority() {
        String uid = (String)request.getAttribute(Config.CURRENT_UID_ATTRIBUTE);
        String uri = request.getRequestURI();
        Optional<UserEntity> user = userRepository.findById(uid);
        Optional<AuthorityEntity> authority = authorityRepository.findByUri(pattern.matcher(uri).replaceAll("*"));
        if (!authority.isPresent()) {
            return true;
        } else if (user.isPresent()) {
            TypeGroupEntity type = user.get().getType();
            if (type != null) {
                for (RoleEntity role : type.getRoles()) {
                    if (role.getAuthorities().contains(authority.get())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Transactional(rollbackFor = {})
    public boolean checkDataAccessAuthority(String uri) {
        String uid = (String)request.getAttribute(Config.CURRENT_UID_ATTRIBUTE);
        Optional<UserEntity> user = userRepository.findById(uid);
        Optional<AuthorityEntity> authority = authorityRepository.findByUri(uri);
        if (!authority.isPresent()) {
            return true;
        } else if (!user.isPresent()) {
            return false;
        } else {
            return user.get().getDataAccessAuths().contains(authority.get());
        }
    }


}
