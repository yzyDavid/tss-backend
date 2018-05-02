package tss.interceptors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import tss.configs.Config;
import tss.entities.AuthorityEntity;
import tss.entities.RoleEntity;
import tss.entities.SessionEntity;
import tss.annotations.session.Authorization;
import tss.entities.UserEntity;
import tss.repositories.AuthorityRepository;
import tss.repositories.SqlSessionRepository;
import tss.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @author yzy
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    private final SqlSessionRepository sqlSessionRepository;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Autowired
    public AuthorizationInterceptor(SqlSessionRepository sqlSessionRepository, UserRepository userRepository,
                                    AuthorityRepository authorityRepository) {
        this.sqlSessionRepository = sqlSessionRepository;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        Method method = ((HandlerMethod) handler).getMethod();
        String auth = request.getHeader(Config.AUTH_HEADER);

        if (auth == null && method.getAnnotation(Authorization.class) != null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        if (sqlSessionRepository.existsByToken(auth)) {
            SessionEntity session = sqlSessionRepository.findByToken(auth);
            String uid = session.getUid();
            request.setAttribute(Config.CURRENT_UID_ATTRIBUTE, uid);
            if(!checkAuth(uid, request.getRequestURI()) && method.getAnnotation(Authorization.class) != null) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
        } else {
            // invalid auth provided.
            if (method.getAnnotation(Authorization.class) != null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
        }

        return true;
    }

    private boolean checkAuth(String uid, String uri) {
        Optional<UserEntity> ret = userRepository.findById(uid);
        if(!ret.isPresent()) {
            return false;
        }
        UserEntity curUser = ret.get();
        Optional<AuthorityEntity> auth = authorityRepository.findByUri(uri);
        if(!auth.isPresent())
            return true; // no need to check
        for(RoleEntity role : curUser.getRoles()) {
            if(role.getAuthorities().contains(auth.get())) {
                return true;
            }
        }
        return false;
    }

}
