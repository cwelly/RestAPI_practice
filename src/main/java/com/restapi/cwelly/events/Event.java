package com.restapi.cwelly.events;

import java.time.LocalDateTime;
// 자바 빈 스펙에 맞는 형태
// 1. 기본 생성자 존재
// 2. 필드 내에서 게터세터가 있어야함
// 이를 롬복으로 구현

public class Event {
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
    private EventStatus eventStatus;
}
