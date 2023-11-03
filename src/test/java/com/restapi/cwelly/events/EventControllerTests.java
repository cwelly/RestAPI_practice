package com.restapi.cwelly.events;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


// junit5 로 오면서 @RunWith(SpringRunner.class)는 아래와 동일한 코드로 이름만 바뀜
@ExtendWith(SpringExtension.class)
// 웹과 관련된 빈들이 모두 등록된다
// 테스트에서 목관련된 빈들을 사용할수도 있다 아래와 같이
// 목킹된 디스패처 서블릿을 상대로 가짜요청을 보내고 응답확인을 이 테스트안에서 할 수 있다.
@WebMvcTest

//알아야 할건 이건 EventController의 단위테스트 라곤 보긴 어렵다
// only EventController여야 단위테스트인데 지금은 목킹된 DS도 껴있기도 함
public class EventControllerTests {
    
    // 디스패처 서블릿을 목킹
    @Autowired
    MockMvc mockMvc;
    // 이 MockMvc는 웹서버를 띄우지 않아서 조금 빠르지만,
    // 그자체로 DS이기 때문에 단위테스트보단 느리다 !

    @Test
    public void createEvent() throws Exception {
        // perform 안에 있는 매개인자는 내가 확인하고 싶은 요청 넣으면 됨
        mockMvc.perform(post("/api/events/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON)
                )
                .andExpect(status().isCreated());
    }


    
}
