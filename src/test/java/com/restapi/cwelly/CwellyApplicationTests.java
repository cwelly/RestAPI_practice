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
		// Given
		String name = "Event";
		String description = "Spring";

		// When
		Event event = new Event();
		event.setName(name);
		event.setDescription(description);

		// 여기서 단축키 꿀팁
		// 아래와 같이 "something"을 중복해서 사용하는경우
		// 이를 변수화 해주고 , 선언까지 해주는 단축키
		// 바로 커서로 범위 지정해주고 , ctrl + alt + v 해주면 끝

		// 여기서 단축키 꿀팁
		// 해당 테스트를 바로실행하려면 ctrl + shift + F10


		// Then
		assertThat(event.getName()).isEqualTo(name);
		assertThat(event.getDescription()).isEqualTo(description);

	}

}
