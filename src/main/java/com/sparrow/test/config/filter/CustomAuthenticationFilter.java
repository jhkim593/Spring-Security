package com.sparrow.test.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparrow.test.config.JwtTokenProvider;
import com.sparrow.test.config.auth.CustomUserDetails;
import com.sparrow.test.dto.UserLoginDto;
import com.sparrow.test.dto.UserLoginResponseDto;
import com.sparrow.test.exception.UserNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
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

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private ObjectMapper mapper=new ObjectMapper();
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    public CustomAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider){
        this.authenticationManager=authenticationManager;
        this.jwtTokenProvider=jwtTokenProvider;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        Authentication authenticate=null;
        PrintWriter writer=null;

        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            writer=response.getWriter();
            UserLoginDto userLoginDto = mapper.readValue(request.getInputStream(), UserLoginDto.class);
            UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(),userLoginDto.getPassword());
            authenticate = authenticationManager.authenticate(token);

        }
        catch (Exception e){
            response.setStatus(401);
            writer.print(mapper.writeValueAsString("로그인에 실패 했습니다."));
        }
        finally {
            return authenticate;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        CustomUserDetails principal = (CustomUserDetails) authResult.getPrincipal();
        String token = jwtTokenProvider.createToken(String.valueOf(principal.getId()));
        PrintWriter writer=response.getWriter();
        UserLoginResponseDto responseDto=new UserLoginResponseDto("JWT","Bearer "+token);
        writer.print(mapper.writeValueAsString(responseDto));

    }
}
