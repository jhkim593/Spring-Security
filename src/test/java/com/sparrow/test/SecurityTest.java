package com.sparrow.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparrow.test.dto.UserLoginDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc

public class SecurityTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void test()throws Exception{

        String content = objectMapper.writeValueAsString(new UserLoginDto("test", "test"));

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isUnauthorized())
                .andDo(MockMvcResultHandlers.print());

    }

//    @Test
//    public void authorizationTest()throws Exception{
//        mockMvc.perform(MockMvcRequestBuilders.get("/user/test")
//                        .contentType(MediaType.APPLICATION_JSON).
//
//                        header(HttpHeaders.AUTHORIZATION,"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwiaWF0IjoxNjM0MDI2NTE3LCJleHAiOjE2MzQwMzAxMTd9.J7IPodrbxX2J8FTtKXINFgePZI_0IoiLCG2ZISUlYJg"))
//                .andExpect(status().isOk())
//                .andDo(MockMvcResultHandlers.print());
//    }

    @Test
    public void authorizationTest()throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/test")
                        .contentType(MediaType.APPLICATION_JSON).
                        header(HttpHeaders.AUTHORIZATION,"Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwiaWF0IjoxNjM0MDI2NTE3LCJleHAiOjE2MzQwMzAxMTd9.J7IPodrbxX2J8FTtKXINFgePZI_0IoiLCG2ZISUlYJg"))
                .andExpect(status().isForbidden())
                .andDo(MockMvcResultHandlers.print());
    }

}
