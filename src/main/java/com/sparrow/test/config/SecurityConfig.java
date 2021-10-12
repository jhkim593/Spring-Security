package com.sparrow.test.config;

import com.sparrow.test.config.auth.CustomUserDetailsService;
import com.sparrow.test.config.auth.SecurityAuthenticationFilter;
import com.sparrow.test.config.filter.CustomFilter;
import com.sparrow.test.config.filter.CustomFilter2;
import com.sparrow.test.config.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


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


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.

//                httpBasic().disable()
                csrf().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크
                .antMatchers("/login1"
                ).permitAll()
                .antMatchers("/user/test").hasRole("USER")
                .antMatchers("/admin/test").hasRole("ADMIN")
                .anyRequest().authenticated()// 그외 나머지 요청은 모두 인증된 회원만 접근 가능
                .and()
//                .addFilterBefore(new  SecurityAuthenticationFilter(customUserDetailsService,passwordEncoder), UsernamePasswordAuthenticationFilter.class);
//                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
                .addFilterBefore(new CustomFilter2(jwtTokenProvider),UsernamePasswordAuthenticationFilter.class)
                .addFilter(new CustomFilter(authenticationManager(),jwtTokenProvider));



    }

}




