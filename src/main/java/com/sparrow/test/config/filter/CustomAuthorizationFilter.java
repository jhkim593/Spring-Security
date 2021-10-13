package com.sparrow.test.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparrow.test.UserRepository;
import com.sparrow.test.config.JwtTokenProvider;
import com.sparrow.test.config.auth.CustomUserDetails;
import com.sparrow.test.entity.User;
import com.sparrow.test.exception.UserNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;
    private UserRepository userRepository;
    private ObjectMapper objectMapper=new ObjectMapper();

    public CustomAuthorizationFilter(JwtTokenProvider jwtTokenProvider, UserRepository userRepository){
        this.jwtTokenProvider=jwtTokenProvider;
        this.userRepository=userRepository;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = jwtTokenProvider.resolveToken(request);
        PrintWriter writer=null;

        if(token == null || !token.startsWith("Bearer")) {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
            filterChain.doFilter(request, response);
            return;
        }
        token=token.replace("Bearer ","");

            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");


        if(token != null && jwtTokenProvider.validateToken(token)) {

            Long userId= Long.valueOf(jwtTokenProvider.getUserPk(token));
            User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
            CustomUserDetails customUserDetails=new CustomUserDetails(user.getId(),user.getEmail(),user.getPassword(),new SimpleGrantedAuthority(user.getRole()));
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(customUserDetails,null, customUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);

//        catch (UserNotFoundException e){
//            writer= response.getWriter();
//            response.setStatus(401);
//            writer.print(objectMapper.writeValueAsString("등록된 회원이 아닙니다."));
//        }
//        catch(ExpiredJwtException e) {
//            writer= response.getWriter();
//            response.setStatus(401);
//            writer.print(objectMapper.writeValueAsString("토큰 유효기간이 만료되었습니다."));
//        }
//        catch (Exception e) {
//            writer= response.getWriter();
//            response.setStatus(401);
//            writer.print(objectMapper.writeValueAsString("토큰 인증에 실패했습니다."));
//
//        }
    }

}
