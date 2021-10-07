package com.sparrow.test.Service;

import com.sparrow.test.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VerifyTest {

    @Mock
    UserService userService;

    @Mock
    UserService2 userService2;

    @Test
    public void testVerifyTime(){

        userService.getUser();
        userService.getUser();
        verify(userService,times(2)).getUser();
    }
    @Test
    public void testVerifyNever(){

        verify(userService,never()).getUser();
    }
    @Test
    public void testAtLeastOne(){
        userService.getUser();
        verify(userService,atLeastOnce()).getUser();

    }
    @Test
    public void testAtLeast(){
        userService.getUser();
        userService.getUser();
        userService.getUser();
        verify(userService,atLeast(2)).getUser();  //최소 두번
    }
    @Test
    public void testAtMostOne(){

        userService.getUser();
        verify(userService,atMostOnce()).getUser();
    }
    @Test
    public void testAtMost(){
        userService.getUser();
        userService.getUser();
        userService.getUser();

        verify(userService,atMost(3)).getUser();


    }
    @Test
    public void testOnly(){
        userService.getUser();
        verify(userService,only()).getUser();

    }

    @Test
    public void testInOrder(){
        userService.getUser();
        userService.getNum();
        InOrder inOrder = inOrder(userService);
        inOrder.verify(userService).getUser();
        inOrder.verify(userService).getNum();

    }

    @Test
    public void testInOrderWithCalls(){

        userService.getUser();
        userService.getUser();
        userService.getNum();

        InOrder inOrder = inOrder(userService);
        inOrder.verify(userService,calls(2)).getUser();  //메소드 실행횟수 calls 사용
        inOrder.verify(userService).getNum();

    }

    //verify 후에 userService 사용하게 되면 fail 발생
    @Test
    public void testInOrderWithVerifyNoMoreInteractions(){

        userService.getUser();


        InOrder inOrder = inOrder(userService);

        inOrder.verify(userService).getUser();

        verifyNoMoreInteractions(userService);

    }


    //userService2를 호출하면 fail발생
    @Test
    void testInOrderWithVerifyNoInteractions() {
        userService.getUser();
        userService.getNum();


        InOrder inOrder = inOrder(userService);

        inOrder.verify(userService).getUser();
        inOrder.verify(userService).getNum();

        verifyNoInteractions(userService2);
    }

    @Test
    public void test(){
        //given
        given(userService.getUser()).willReturn(new User(1L,"@2"));

        //when
        userService.getUser();
        userService.getUser();

        //then
        then(userService).should(times(2)).getUser();


    }
}