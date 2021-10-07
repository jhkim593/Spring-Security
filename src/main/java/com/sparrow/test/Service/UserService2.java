package com.sparrow.test.Service;

import com.sparrow.test.entity.User;
import com.sparrow.test.entity.User2;
import org.springframework.stereotype.Service;

@Service
public class UserService2 {

    public User2 getUser2(){
        return new User2(1L,3);
    }
}
