package com.sparrow.test.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static java.util.Base64.*;
import static org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder.decode;


public class SecurityAuthenticationFilter extends OncePerRequestFilter {
    private CustomUserDetailsService customUserDetailsService;
    private PasswordEncoder passwordEncoder;



    public SecurityAuthenticationFilter(CustomUserDetailsService customUserDetailsService,PasswordEncoder passwordEncoder){
        this.customUserDetailsService=customUserDetailsService;
        this.passwordEncoder=passwordEncoder;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Basic ")) {
            filterChain.doFilter(request, response);
            return;
        }
        header=header.replace("Basic ","");
        // Decode
        byte[] e = decode(header);
        String usernameWithPassword = new String(e);

        if(!usernameWithPassword.isEmpty()&&!usernameWithPassword.contains(":")){
            response.setStatus(401);
            filterChain.doFilter(request, response);
            return;
        }
        // Split the username from the password
        String username = usernameWithPassword.substring(0, usernameWithPassword.indexOf(":"));
        String password = usernameWithPassword.substring(usernameWithPassword.indexOf(":") + 1);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        if(passwordEncoder.matches(password,userDetails.getPassword())) {

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(token);

        }
        else{
            response.setStatus(401);
        }
        filterChain.doFilter(request, response);
    }
}
