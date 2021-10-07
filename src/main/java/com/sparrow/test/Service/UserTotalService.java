package com.sparrow.test.Service;

import com.sparrow.test.entity.User;
import com.sparrow.test.entity.User2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserTotalService {
    private final UserService userService;
    private final UserService2 userService2;

    public User getUser(){
        return userService.getUser();
    }
    public User2 getUser2(){
        return userService2.getUser2();
    }

}
