package com.sparrow.test.config.auth;

import com.sparrow.test.UserRepository;
import com.sparrow.test.entity.User;
import com.sparrow.test.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            return new CustomUserDetails(
                Long.valueOf(user.getId()),
                user.getEmail(),
                user.getPassword(),
                new SimpleGrantedAuthority(user.getRole()));
        }
        else throw new UsernameNotFoundException("해당 유저를 찾을 수없습니다.");

    }
}
