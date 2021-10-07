package com.sparrow.test.config;

import com.sparrow.test.UserRepository;
import com.sparrow.test.config.auth.AuthenticFilter;
import com.sparrow.test.config.auth.CustomBasicAuthenticationFilter;
import com.sparrow.test.config.auth.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;
    UsernamePasswordAuthenticationFilter authenticationFilter =null;


//    @Bean
//    public UsernamePasswordAuthenticationFilter authenticationFilter() throws Exception {
//        AuthenticFilter filter = new AuthenticFilter(authenticationManager(),userRepository,customUserDetailsService);
//        filter.setAuthenticationManager(authenticationManager());
//        filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
//        filter.setUsernameParameter("email");
//        filter.setPasswordParameter("password");
//
//        return filter;
//    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        authenticationFilter=authenticationFilter();

            http
                    .logout()
                    .logoutUrl("/api/logout").and()
                    .csrf()
                    .disable()

                    .addFilter(new AuthenticFilter(authenticationManager(),userRepository,customUserDetailsService))
//                    .addFilterAt(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                    .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                    .addFilter(new CustomBasicAuthenticationFilter(authenticationManager(),null))

                    .authorizeRequests()
                    .antMatchers(
                            "/signUp","/test","/login"
                    ).permitAll()
                    .anyRequest().authenticated()
                    .and()
//                    .headers().frameOptions().sameOrigin()
//                    .and()

                    .httpBasic().disable();
//                    .authenticationEntryPoint(new NoPopupBasicAuthenticationEntryPoint()) ;
//            http.authenticationProvider(authProvider);
        }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}

