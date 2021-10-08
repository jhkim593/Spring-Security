package com.sparrow.test.config.auth;


import com.sparrow.test.UserRepository;
import com.sparrow.test.config.auth.CustomUserDetails;
import com.sparrow.test.entity.User;
import com.sparrow.test.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService{



    private final UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String userPk) {

        User user = userRepository.findUserByEmail(userPk).orElseThrow(UserNotFoundException::new);
        return new CustomUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                new SimpleGrantedAuthority(user.getRole()));
    }


}

