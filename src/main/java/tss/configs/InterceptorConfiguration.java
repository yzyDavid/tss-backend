package tss.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tss.interceptor.AuthorizationInterceptor;
import tss.session.SqlSessionRepository;

/**
 * @author yzy
 */
@Configuration
public class InterceptorConfiguration {
    private final SqlSessionRepository sqlSessionRepository;

    @Autowired
    public InterceptorConfiguration(SqlSessionRepository sqlSessionRepository) {
        this.sqlSessionRepository = sqlSessionRepository;
    }

    @Bean
    public WebMvcConfigurer addAuthorizationInterceptor() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new AuthorizationInterceptor(sqlSessionRepository));
            }
        };
    }

    @Bean
    public WebMvcConfigurer addUserInterceptor() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                //TODO
            }
        };
    }
}
