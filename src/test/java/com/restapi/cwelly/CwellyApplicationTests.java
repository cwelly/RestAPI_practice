package com.restapi.cwelly;

import com.restapi.cwelly.events.Event;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
class CwellyApplicationTests {

	@Test
	public void builder(){
		// 이벤트 생성
		Event event = Event.builder()
				// 빌더를 활용하면 명시적으로 알 수 있음
				.name("Cwelly String Rest API")
				.description(" 레스트 API 개발 스프링")
				.build();
		//assertJ 가 제공하는 라이브러리 임포트
		assertThat(event).isNotNull();
	}
	@Test
	public void javaBean(){
		Event event = new Event();
		String name = "Event";
		event.setName(name);
		event.setDescription("Spring");
		assertThat(event.getName()).isEqualTo(name);

	}

}
