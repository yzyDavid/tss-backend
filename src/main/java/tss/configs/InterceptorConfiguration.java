package tss.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tss.interceptors.AuthorizationInterceptor;
import tss.interceptors.CurrentUserInterceptor;
import tss.repositories.AuthorityRepository;
import tss.repositories.SqlSessionRepository;
import tss.repositories.UserRepository;

import java.util.List;

/**
 * @author yzy
 */
@Configuration
public class InterceptorConfiguration {
    private final SqlSessionRepository sqlSessionRepository;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    @Autowired
    public InterceptorConfiguration(SqlSessionRepository sqlSessionRepository, UserRepository userRepository,
                                    AuthorityRepository authorityRepository) {
        this.sqlSessionRepository = sqlSessionRepository;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    @Bean
    public WebMvcConfigurer addAuthorizationInterceptor() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new AuthorizationInterceptor(sqlSessionRepository, userRepository, authorityRepository));
            }
        };
    }

    @Bean
    public WebMvcConfigurer addUserArgumentSolver() {
        return new WebMvcConfigurer() {
            @Override
            public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
                resolvers.add(new CurrentUserInterceptor(userRepository));
            }
        };
    }
}
