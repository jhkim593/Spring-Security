package com.sparrow.test.Service;

import com.sparrow.test.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {



    @Spy
    UserService userService;

    @Test
    public void getUser(){
        User user = userService.getUser();
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("test");

    }
    @Test
    public void getUserWithStubbing(){
        User dummyUser=new User(2L,"test2");
        when(userService.getUser()).thenReturn(dummyUser);
        User user = userService.getUser();
        assertThat(user.getId()).isEqualTo(2L);
        assertThat(user.getName()).isEqualTo("test2");

    }

    @Test
    public void getUser1(){
        User dummyUser=new User(2L,"test");
        when(userService.getUser()).thenReturn(dummyUser);
        User user = userService.getUser();
        assertThat(user.getId()).isEqualTo(2L);
    }
    @Test
    public void getUser2(){
        assertThat(userService.getUser()).isNull();
    }



}