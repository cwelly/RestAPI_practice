package com.restapi.cwelly.events;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@RequestMapping(value = "/api/events/" , produces = MediaTypes.HAL_JSON_VALUE)
// 위의 설정대로하면 produces에 따라 이 클래스의 핸들러들은
// HAL_FORMS_JSON_VALUE 형식으로 응답을 보내게 설정된것이다.
// 또한 value에 따라 모든 핸들러들은 "/api/events"를 기본 경로로 사용하게 된다.
public class EventController {

    private final EventRepository eventRepository;

    private final ModelMapper modelMapper;
    // 받아올 파라미터가 이미 빈에 등록되어있다면 @Autowired생략 가능
    public EventController(EventRepository eventRepository, ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity createEvent(@RequestBody EventDto eventDto){
        // 입력으로 받은 값이 DB에 저장하게 하는 코드
        Event event = modelMapper.map(eventDto , Event.class);
        Event newEvent = this.eventRepository.save(event);
        // 새로 EventDto를 만들었기 때문에 이를 사용하려면
        // 원래라면 위의 EventDto의 값을 Event에 옮겨야 한다
        //

        URI createdUri = linkTo(EventController.class).slash(newEvent.getId()).toUri();
        event.setId(10);
        return ResponseEntity.created(createdUri).body(event);
    }
}
