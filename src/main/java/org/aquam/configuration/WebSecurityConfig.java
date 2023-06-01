package org.aquam.configuration;

import lombok.RequiredArgsConstructor;
import org.aquam.configuration.authentication.UserAuthenticationProvider;
import org.aquam.configuration.token.TokenValidatorFilter;
import org.aquam.service.impl.AppUserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// instead of WebSecurityConfigurerAdapter
// Manages Users
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final CorsConfig corsConfig;
    private final TokenValidatorFilter tokenValidatorFilter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AppUserServiceImpl appUserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        corsConfig.corsConfiguration(http);
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/nikita/**").permitAll()
                .antMatchers(HttpMethod.POST, "/signin/**").permitAll()
                .antMatchers(HttpMethod.POST, "/signup/**").permitAll()
                .antMatchers(HttpMethod.POST, "/signup/check/**").permitAll()
                .antMatchers(HttpMethod.GET, "/signup/confirm/**").permitAll()
                .antMatchers(HttpMethod.GET, "/pattern/image/**").permitAll()
                .antMatchers(HttpMethod.GET, "/purple-bee/health").permitAll()
                .anyRequest().authenticated()
                // .and().formLogin().loginPage("/login2")
                .and().addFilterBefore(tokenValidatorFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(new UserAuthenticationProvider(appUserService, bCryptPasswordEncoder));
        return http.build();
    }

}
