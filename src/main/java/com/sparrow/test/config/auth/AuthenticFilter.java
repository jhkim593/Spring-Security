package com.sparrow.test.config.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparrow.test.UserRepository;
import com.sparrow.test.dto.UserLoginDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class AuthenticFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private ObjectMapper objectMapper=new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        PrintWriter printWriter=null;
        Authentication authenticate = null;

        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            printWriter=response.getWriter();
            UserLoginDto userLoginDto = objectMapper.readValue(request.getInputStream(), UserLoginDto.class);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userLoginDto.getEmail());
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(token);
            authenticate=authenticationManager.authenticate(token);



        }
        catch (UsernameNotFoundException e){

            printWriter.print("유저를 찾을 수 없습니다.");

        }
        catch (Exception e){
            e.printStackTrace();
            printWriter.print("로그인에 실패했습니다.");
        }finally {
            return authenticate;
        }





}

    private UserRepository userRepository;
    private CustomUserDetailsService customUserDetailsService;

    public AuthenticFilter(AuthenticationManager authenticationManager,UserRepository userRepository,CustomUserDetailsService customUserDetailsService){
        this.authenticationManager=authenticationManager;
        this.userRepository=userRepository;
        this.customUserDetailsService=customUserDetailsService;
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PrintWriter printWriter= response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        printWriter.print("로그인에 성공했습니다.");


    }
}
