package com.restapi.cwelly.events;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import lombok.*;
import org.springframework.data.annotation.Id;


// 자세한 구현 코드들은
// target 디렉토리 밑에 같은 이름의 Event에서 확인가능( 생성자나 게터세터 등)
import java.time.LocalDateTime;
// 자바 빈 스펙에 맞는 형태
// 1. 기본 생성자 존재
// 2. 필드 내에서 게터세터가 있어야함
// 이를 롬복으로 구현
@Builder @NoArgsConstructor @AllArgsConstructor
@Getter @Setter @EqualsAndHashCode(of="id")
@Entity
// @EqualsAndHashCode(of="id")
// 모든 필드를 사용하지만 , 나중에 엔티티간의 연관관계가 있을때 ,
// Equals 와 Hashcode가 있는 곳에서 상호참조때문에 스택오버플로우가 발생하는것을
// 막기위해 ID를 명시적으로 사용한것

// 우리가 커스텀한 애노테이션으로 만들고
// 그위에 사용할 애노테이션을 사용하면 쓸수 있음 (ex myAnotation 이렇게 사용자 정의 메타애노테이션 사용가능)
// 하지만 롬복은 이러한 메타애노테이션으로 사용할 수 없기때문에 패스

// 여기서 @Data  사용하면 안되는 이유는, 이미 Data와 겹치는 애노테이션읋 적용해뒀기 때문
public class Event {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime beginEnrollmentDateTime;
    private LocalDateTime closeEnrollmentDateTime;
    private LocalDateTime beginEventDateTime;
    private LocalDateTime endEventDateTime;
    private String location; // (optional) 이게 없으면 온라인 모임
    private int basePrice; // (optional)
    private int maxPrice; // (optional)
    private int limitOfEnrollment;
    private boolean offline ;
    private boolean free;
    // EnumType에는 ordinal 과 String이 있는데,
    // 순서를 우선시하는 ordinal은 나중에 enum의 순서가 바뀌면 데이터가
    // 꼬일 위험을 피하고자 String 사용
    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;
}
