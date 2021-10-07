package com.sparrow.test.Service;

import com.sparrow.test.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserTotalServiceTest {

    @Mock
    UserService userService;

    @Spy
    UserService2 userService2;

    @InjectMocks
    UserTotalService userTotalService;

    @Test
    public void testThenReturn() {
        User dummyUser = new User(10L, "test1");
        when(userService.getUser()).thenReturn(dummyUser);
        User user = userService.getUser();
        assertThat(user.getId()).isEqualTo(10L);
        assertThat(user.getName()).isEqualTo("test1");

    }
    @Test
    public void testThenAnswer() {
        when(userService.getUserWithIdAndName1(any(), any())).thenAnswer((Answer) invocation -> {
            Object[] args = invocation.getArguments();
            return new User((Long) args[0] + 3L, args[1] + "d");

        });
        User user = userService.getUserWithIdAndName1(1L, "test");
        assertThat(user.getId()).isEqualTo(4L);
        assertThat(user.getName()).isEqualTo("testd");
    }

    @Test
    public void testThenThrows() {
        when(userService.getUser()).thenThrow(new RuntimeException());
        assertThatThrownBy(() -> {
            userService.getUser();
        }).isInstanceOf(RuntimeException.class);


    }
    @Test
    public void testThenCallRealMethod(){
        when(userService.getUser()).thenCallRealMethod();
        User user = userService.getUser();
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("test");

    }


    @Test
    public void getUser() {

        assertThat(userTotalService.getUser()).isNull();

    }

    @Test
    public void getUser2() {

        assertThat(userTotalService.getUser2().getId()).isEqualTo(1L);
        assertThat(userTotalService.getUser2().getAge()).isEqualTo(3);
    }

    @Test
    public void stubbingTest1() {
        User dummyUser1 = new User(1L, "dummy1");
        User dummyUser2 = new User(2L, "dummy2");
        when(userService.getUser()).thenReturn(dummyUser1).thenReturn(dummyUser2);
        User user1 = userService.getUser();
        User user2 = userService.getUser();
        assertThat(user1.getId()).isEqualTo(1L);
        assertThat(user1.getName()).isEqualTo("dummy1");
        assertThat(user2.getId()).isEqualTo(2L);
        assertThat(user2.getName()).isEqualTo("dummy2");

    }

    @Test
    public void stubbingTest() {
        User dummyUser1 = new User(1L, "dummy1");
        when(userService.getUser()).thenReturn(dummyUser1).thenThrow(new RuntimeException());
        User user1 = userService.getUser();
        assertThat(user1.getId()).isEqualTo(1L);
        assertThat(user1.getName()).isEqualTo("dummy1");
        assertThatThrownBy(() -> {
            userService.getUser();
        }).isInstanceOf(RuntimeException.class);


    }

    @Test
    public void stubbingArgumentMatchers() {
        when(userService.getUserWithIdAndName(anyLong(), anyString())).thenReturn("test");
        assertThat(userService.getUserWithIdAndName(1L, "test1")).isEqualTo("test");
        assertThat(userService.getUserWithIdAndName(2L, "test2")).isEqualTo("test");
    }
    @Test
    public void testDoReturn(){

        User user=new User(2L,"test");
        doReturn(user).when(userService).getUser();
        assertThat(userService.getUser()).isEqualTo(user);

    }
    @Test
    public void testDoThrow(){
        doThrow(new RuntimeException()).when(userService).updateUser();
        assertThatThrownBy(()-> userService.updateUser()).isInstanceOf(RuntimeException.class);

    }

}