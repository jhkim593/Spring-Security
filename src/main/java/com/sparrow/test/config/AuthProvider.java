package com.sparrow.test.config;

import com.sparrow.test.Service.UserService;
import com.sparrow.test.UserRepository;
import com.sparrow.test.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("authProvider")
/**
 * 로그인 확인 하는 클레스
 * @author atcis
 *
 */
public class AuthProvider implements AuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String id = authentication.getName();
        String password = authentication.getCredentials().toString();

        System.out.println("id");
        System.out.println(password);

        User user = userService.getUser();

        if (null == user || !passwordEncoder.matches(password, user.getPassword())) {
            return null;
        }

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
//        if (user.isAdmin()) {
//            grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
//        }
//        return new MyAuthenticaion(id, password, grantedAuthorityList, user);
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}