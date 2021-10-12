package com.sparrow.test.config.auth;


import com.sparrow.test.UserRepository;
import com.sparrow.test.config.auth.CustomUserDetails;
import com.sparrow.test.entity.User;
import com.sparrow.test.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService{



    private final UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        

        User user = userRepository.findUserByEmail(username).orElseThrow(()->new UsernameNotFoundException("등록되지 않은 회원입니다."));
        return new CustomUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                new SimpleGrantedAuthority(user.getRole()));
    }


}

