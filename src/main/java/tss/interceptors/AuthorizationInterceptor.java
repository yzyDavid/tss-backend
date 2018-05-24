package tss.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import tss.annotations.session.Authorization;
import tss.configs.Config;
import tss.entities.*;
import tss.repositories.SqlSessionRepository;
import tss.repositories.UserRepository;
import tss.services.AuthorizationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author yzy
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    private final SqlSessionRepository sqlSessionRepository;
    private final UserRepository userRepository;
    private final AuthorizationService authorizationService;

    public AuthorizationInterceptor(SqlSessionRepository sqlSessionRepository, UserRepository userRepository,
                                    AuthorizationService authorizationService) {
        this.sqlSessionRepository = sqlSessionRepository;
        this.userRepository = userRepository;
        this.authorizationService = authorizationService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        Method method = ((HandlerMethod) handler).getMethod();
        if(method.getAnnotation(Authorization.class) == null) {
            return true;
        }

        String auth = request.getHeader(Config.AUTH_HEADER);
        if (auth == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        if (sqlSessionRepository.existsByToken(auth)) {
            SessionEntity session = sqlSessionRepository.findByToken(auth);
            String uid = session.getUid();
            request.setAttribute(Config.CURRENT_UID_ATTRIBUTE, uid);
            if(authorizationService.checkMethodAccessAuthority()) {
                return true;
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
}
