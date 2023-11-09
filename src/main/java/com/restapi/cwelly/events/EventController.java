package com.restapi.cwelly.events;

import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@RequestMapping(value = "/api/events/" , produces = MediaTypes.HAL_JSON_VALUE)
// 위의 설정대로하면 produces에 따라 이 클래스의 핸들러들은
// HAL_FORMS_JSON_VALUE 형식으로 응답을 보내게 설정된것이다.
// 또한 value에 따라 모든 핸들러들은 "/api/events"를 기본 경로로 사용하게 된다.
public class EventController {
    @PostMapping
    public ResponseEntity createEvent(@RequestBody Event event){
        URI createdUri = linkTo(EventController.class).slash("{id}").toUri();
        event.setId(10);
        return ResponseEntity.created(createdUri).body(event);
    }
}
