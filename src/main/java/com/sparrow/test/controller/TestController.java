package com.sparrow.test.controller;

import com.sparrow.test.Service.UserService;
import com.sparrow.test.UserRepository;
import com.sparrow.test.dto.TestDto;
import com.sparrow.test.dto.UserSignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final UserService userService;
    private final UserRepository userRepository;







    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/user/test")
    public String userTest() {
        return "userTest";
    }

    @GetMapping("/admin/test")
    public String adminTest() {
        return "adminTest";
    }

    @GetMapping("/user/{id}")
    public ResponseEntity getUserDetail(@PathVariable("id")Long id){
        return new ResponseEntity(userService.getUserDetail(id),HttpStatus.OK);
    }




//    @GetMapping("/test")
//    public ResponseEntity apiTest(@RequestBody TestDto testDto){
//        return new ResponseEntity(new TestDto(testDto.getTest(), testDto.getTest2()), HttpStatus.OK);
//    }
//    @PostMapping("/hello")
//    public ResponseEntity apiTest(){
//        return new ResponseEntity("ㅎㅇ", HttpStatus.OK);
//    }
    @PostMapping("/signUp")
    public ResponseEntity apiTest1(@RequestBody UserSignUpRequestDto requestDto){
        System.out.println("111");
        userService.signUp(requestDto);
        return new ResponseEntity("회원가입 성공", HttpStatus.OK);

    }

}
