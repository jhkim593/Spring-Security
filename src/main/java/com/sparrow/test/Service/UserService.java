package com.sparrow.test.Service;

import com.sparrow.test.UserRepository;
import com.sparrow.test.config.redis.CacheKey;
import com.sparrow.test.dto.UserDetailDto;
import com.sparrow.test.dto.UserSignUpRequestDto;
import com.sparrow.test.entity.User;
import com.sparrow.test.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional

public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;




    @Cacheable(value = CacheKey.USER,key = "#id",unless = "#result==null")
    public UserDetailDto getUserDetail(Long id){
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return new UserDetailDto(user.getId(),user.getName(),user.getEmail());
    }







    public void signUp(UserSignUpRequestDto userSignUpRequestDto){
        userRepository.save(User.createUser(userSignUpRequestDto.getEmail(),passwordEncoder.encode(userSignUpRequestDto.getPassword()),userSignUpRequestDto.getName()));

    }
    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }


    public User getUser() {
        return new User(1L, "test","gg","ROLE_USER","GG");
    }

    public int getNum() {
        return 1;
    }

    public void updateUser() {

    }

    //
    public String getUserWithIdAndName(Long id, String name) {
        return "test";
    }

    public User getUserWithIdAndName1(Long id, String name) {
        return new User(1L, "test","gg","ROLE_USER","GG");
    }

    public void updateUser2() {

    }



}
