package com.restapi.cwelly.events;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.JsonPath;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


// junit5 로 오면서 @RunWith(SpringRunner.class)는 아래와 동일한 코드로 이름만 바뀜
@ExtendWith(SpringExtension.class)
// 웹과 관련된 빈들이 모두 등록된다
// 테스트에서 목관련된 빈들을 사용할수도 있다 아래와 같이
// 목킹된 디스패처 서블릿을 상대로 가짜요청을 보내고 응답확인을 이 테스트안에서 할 수 있다.
//@WebMvcTest

@SpringBootTest
// 위의 어노테이션에서 mock-mvc를 쓰려면 아래 어노테이션을 ㅆ어ㅑ함
@AutoConfigureMockMvc
//알아야 할건 이건 EventController의 단위테스트 라곤 보긴 어렵다
// only EventController여야 단위테스트인데 지금은 목킹된 DS도 껴있기도 함
public class EventControllerTests {
    
    // 디스패처 서블릿을 목킹
    @Autowired
    MockMvc mockMvc;
    // 이 MockMvc는 웹서버를 띄우지 않아서 조금 빠르지만,
    // 그자체로 DS이기 때문에 단위테스트보단 느리다 !

    @Autowired
    ObjectMapper objectMapper;

    //위의 WebMvcTest만으로는 Repository 를 빈에 등록해주지 않는다
    // 그렇기에 목킹할 수 있는 빈을 등록
//    @MockBean
//    EventRepository eventRepository;

    @Test
    public void createEvent() throws Exception {
        EventDto event = EventDto.builder()
                // DTO로 민감한 값은 받지 않게 설정
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2018,11,23,14,21))
                .closeEnrollmentDateTime(LocalDateTime.of(2018,11,23,14,22))
                .beginEventDateTime(LocalDateTime.of(2018,11,25,14,21))
                .endEventDateTime(LocalDateTime.of(2018,11,26,14,21))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타텁 팩토리")
                .build();
        //이런 요청을 줘야하는데 본문에 주는 방법은
        // 아래에서 Json으로 바꿔주기로 했다
//        event.setId(10);
        //콜백느낌으로
        //"eventRepository 에 save가 호출되면 , event변수를 리턴해라
//        Mockito.when(eventRepository.save(event)).thenReturn(event);
        // 여기서 EventDto를 추가하고 나면 NullPointerException이 뜬다 , 왜?
        // 위의 Mock 함수는 콜백함수 처럼 , eventRpository에서 save가 발생했을때 , 그 save의 인자가
        // 이 테스트에서 새로만든 위의 event객체 일때 , 그렇다면(thenReturn) event 객체를 리턴하라는 의미다.
        // 근데 save함수가 호출될때 , 인자로 받은 값이 아니라 인자의 값을 변형시켜 호출한값을 넣게 되도록 바뀌었기 때문에 리턴이 null이 된다.


        // perform 안에 있는 매개인자는 내가 확인하고 싶은 요청 넣으면 됨
        mockMvc.perform(post("/api/events/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON)
                // parameter로 줄 event 클래스를 Json형식으로 변환시키는 메소드
                        .content(objectMapper.writeValueAsString(event))
                )
                // 이렇게 하면 실행결과를 찍어보기가능
                .andDo(print())
                // 이렇게 하면 실행결과를 찍어보기가능
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                // "Location" 보다 타입 세이프 한 것.
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("id").value(Matchers.not(100)))
                .andExpect(jsonPath("eventStatus").value((EventStatus.DRAFT.name())))
                .andExpect(jsonPath("free").value(Matchers.not(true)));

    }
    @Test
    public void createEvent_Bad_Request() throws Exception {
        Event event = Event.builder()
                .id(100)
                //이런 값이 들어오면 안된다.
                // 알아서 계산되어야 하는 값들을 제한시켜줘야한다
                .name("Spring")
                .description("REST API Development with Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2018,11,23,14,21))
                .closeEnrollmentDateTime(LocalDateTime.of(2018,11,23,14,22))
                .beginEventDateTime(LocalDateTime.of(2018,11,25,14,21))
                .endEventDateTime(LocalDateTime.of(2018,11,26,14,21))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .free(true)
                .eventStatus(EventStatus.PUBLISHED)
                .location("강남역 D2 스타텁 팩토리")
                .build();


        // perform 안에 있는 매개인자는 내가 확인하고 싶은 요청 넣으면 됨
        mockMvc.perform(post("/api/events/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaTypes.HAL_JSON)
                        // parameter로 줄 event 클래스를 Json형식으로 변환시키는 메소드
                        .content(objectMapper.writeValueAsString(event))
                )
                // 이렇게 하면 실행결과를 찍어보기가능
                .andDo(print())
                // 이렇게 하면 실행결과를 찍어보기가능
                .andExpect(status().isBadRequest())
                // spring boot  의 기능을 이용해 application.properties에
                // "spring.jackson.deserialization.fail-on-unknown-properties=true" 를 추가하여
                // 스프링 부트 단에서 api를 통해 설정되지 않은 값 = dto에 없는 값이 들어올시 unknown 프로퍼티이므로 
                // 이때 BAD_REQUEST를 리턴할 수 있게 만드는 라인
                // 이때 사용한 것은 jackson 의 디시리얼레이션 혹은 시리얼레이션의 하나
        ;

    }

    
}
