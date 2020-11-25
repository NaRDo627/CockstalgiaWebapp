package net.hkpark.config;

import lombok.RequiredArgsConstructor;
import net.hkpark.cockstalgia.core.security.CustomOAuth2Provider;
import net.hkpark.cockstalgia.core.security.LoginSuccessHandler;
import net.hkpark.cockstalgia.core.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final LoginSuccessHandler loginSuccessHandler;

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new net.hkpark.cockstalgia.core.security.LogoutSuccessHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.csrf().disable().headers().frameOptions().disable()
            .and()
                .authorizeRequests()
                .antMatchers("/webjars/**",
                        "/vendor/**",
                        "/img/**",
                        "/css/**",
                        "/scss/**",
                        "/js/**").permitAll()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/register").hasAnyAuthority("NEED_MORE_INFO")
//                .antMatchers("/**").hasRole("USER")
                .antMatchers("/h2-console").hasIpAddress("192.168.0/24")
                .antMatchers("/console/**").permitAll()
                .antMatchers("/**").permitAll()

            .and()
                .oauth2Login()
                .loginPage("/admin/login")
                .userInfoEndpoint().userService(customOAuth2UserService)  // 네이버 USER INFO의 응답을 처리하기 위한 설정
            .and()
                .successHandler(loginSuccessHandler)
                .failureUrl("/loginFailure")
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/admin/login"))
//            .and()
//                .formLogin()
//                .loginPage("/admin/login")
//                .failureUrl("/admin/login?error=true")
//                .usernameParameter("id")
//                .passwordParameter("password")
//                .successHandler(loginSuccessHandler())
//                .permitAll()
            .and().logout()
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/admin/login")
                .permitAll();
    }

//    @Bean
//    public OAuth2AuthorizedClientService authorizedClientService() {
//        return new DatabaseOAuth2AuthorizedClientService();
//    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(
            OAuth2ClientProperties oAuth2ClientProperties,
            @Value("${custom.oauth2.kakao.client-id}") String kakaoClientId,
            @Value("${custom.oauth2.kakao.client-secret}") String kakaoClientSecret,
            @Value("${custom.oauth2.naver.client-id}") String naverClientId,
            @Value("${custom.oauth2.naver.client-secret}") String naverClientSecret) {
        List<ClientRegistration> registrations = oAuth2ClientProperties
                .getRegistration().keySet().stream()
                .map(client -> getRegistration(oAuth2ClientProperties, client))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        registrations.add(CustomOAuth2Provider.KAKAO.getBuilder("kakao")
                .clientId(kakaoClientId)
                .clientSecret(kakaoClientSecret)
                .jwkSetUri("temp")
                .build());

        registrations.add(CustomOAuth2Provider.NAVER.getBuilder("naver")
                .clientId(naverClientId)
                .clientSecret(naverClientSecret)
                .jwkSetUri("temp")
                .build());
        return new InMemoryClientRegistrationRepository(registrations);
    }

    private ClientRegistration getRegistration(OAuth2ClientProperties clientProperties, String client) {
        if("google".equals(client)) {
            OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("google");
            return CommonOAuth2Provider.GOOGLE.getBuilder(client)
                    .clientId(registration.getClientId())
                    .clientSecret(registration.getClientSecret())
                    .scope("email", "profile")
                    .build();
        }

        if("facebook".equals(client)) {
            OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("facebook");
            return CommonOAuth2Provider.FACEBOOK.getBuilder(client)
                    .clientId(registration.getClientId())
                    .clientSecret(registration.getClientSecret())
                    .userInfoUri("https://graph.facebook.com/me?fields=id,name,email,link")
                    .scope("email")
                    .build();
        }

        return null;
    }
}