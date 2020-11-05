package net.hkpark.config;

import lombok.AllArgsConstructor;
import net.hkpark.cockstalgia.core.security.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler("/");
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new net.hkpark.cockstalgia.core.security.LogoutSuccessHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/resources/**", "/register", "/api/**").permitAll()
                .antMatchers("/webjars/**",
                        "/vendor/**",
                        "/img/**",
                        "/css/**",
                        "/scss/**",
                        "/js/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/**").hasRole("USER")
                .and().formLogin()
                .loginPage("/admin/login")
                .failureUrl("/admin/login?error=true")
                .usernameParameter("id")
                .passwordParameter("password")
                .successHandler(loginSuccessHandler())
                .permitAll()
                .and().logout()
                .logoutUrl("/admin/logout")
                .logoutSuccessHandler(logoutSuccessHandler())
                .logoutSuccessUrl("/admin/login")
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}