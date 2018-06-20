package tss.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import tss.configs.Config;

import tss.entities.SessionEntity;
import tss.annotations.session.Authorization;

import tss.entities.*;

import tss.repositories.SqlSessionRepository;
import tss.repositories.UserRepository;
import tss.services.AuthorizationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

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
        if (method.getAnnotation(Authorization.class) == null) {
            return true;
        }

        if (checkTimestamp(request) && authorizationService.checkMethodAccessAuthority()) {
            return true;
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }

    private boolean checkTimestamp(HttpServletRequest request) {
        String token = request.getHeader(Config.AUTH_HEADER);
        if (token != null) {
            Optional<SessionEntity> ret = sqlSessionRepository.findByToken(token);
            if (ret.isPresent()) {
                Date date = new Date();
                SessionEntity session = ret.get();
                if (date.getTime() - session.getTimestamp().getTime() < Config.TOKEN_EXPIRE_TIME) {
                    request.setAttribute(Config.CURRENT_UID_ATTRIBUTE, session.getUid());
                    session.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));
                    sqlSessionRepository.save(session);
                    return true;
                } else {
                    sqlSessionRepository.delete(session);
                    return false;
                }
            }
        }
        return false;
    }
}
