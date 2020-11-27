package net.hkpark.config;

import net.hkpark.cockstalgia.core.interceptor.RequestLoggerInterceptor;
import net.hkpark.cockstalgia.core.interceptor.SessionLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestLoggerInterceptor())
                .excludePathPatterns("/css/**", "/fonts/**", "/plugin/**", "/scripts/**", "/vendor/**", "/js/**", "/error");

        registry.addInterceptor(new SessionLoginInterceptor())
                .addPathPatterns("/admin", "/admin/**")
                .excludePathPatterns("/admin/login", "/css/**", "/fonts/**", "/plugin/**", "/scripts/**", "/vendor/**", "/js/**", "/error");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "/webjars/**",
                "/vendor/**",
                "/img/**",
                "/css/**",
                "/scss/**",
                "/js/**")
                .addResourceLocations(
                        "classpath:/META-INF/resources/webjars/",
                        "classpath:/static/vendor/",
                        "classpath:/static/img/",
                        "classpath:/static/css/",
                        "classpath:/static/scss/",
                        "classpath:/static/js/");
    }
}
