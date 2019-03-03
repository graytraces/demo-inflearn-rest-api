package com.cardi.demoinflearnrestapi.events;

import com.cardi.demoinflearnrestapi.common.TestDescription;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @TestDescription("정상적으로 값을 생성하는 테스트")
    public void createEvent() throws Exception {
        EventDto event = EventDto.builder()
                .name("Spring")
                .description("Rest Api Development With Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 22, 11, 22, 33))
                .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 30, 11, 22, 33))
                .beginEventDateTime(LocalDateTime.of(2018, 11, 22, 11, 22, 33))
                .endEventDateTime(LocalDateTime.of(2018, 11, 30, 11, 22, 33))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타트업 팩토리")
                .build();

        mockMvc.perform( post("/api/events/")
                    .contentType((MediaType.APPLICATION_JSON_UTF8))
                    .accept(MediaTypes.HAL_JSON)
                    .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("id").value(Matchers.not(100)))
                .andExpect(jsonPath("free").value(Matchers.not(true)))
                .andExpect(jsonPath("eventStatus").value(EventStatus.DRAFT.name()));
    }

    @Test
    @TestDescription("입력받을수 없는 값을 파라미터로 쓰는 경우")
    public void createEvent_BadRequest() throws Exception {
        Event event = Event.builder()
                .id(100)
                .name("Spring")
                .description("Rest Api Development With Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 22, 11, 22, 33))
                .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 30, 11, 22, 33))
                .beginEventDateTime(LocalDateTime.of(2018, 11, 22, 11, 22, 33))
                .endEventDateTime(LocalDateTime.of(2018, 11, 22, 11, 22, 33))
                .basePrice(100)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타트업 팩토리")
                .free(true)
                .offline(false)
                .build();

        mockMvc.perform( post("/api/events/")
                .contentType((MediaType.APPLICATION_JSON_UTF8))
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @TestDescription("입력값이 비어있는 경우")
    public void createEvent_Bad_Request_Empty_Input() throws Exception {
        EventDto eventDto = EventDto.builder().build();

        this.mockMvc.perform(post("/api/events")
                .contentType((MediaType.APPLICATION_JSON_UTF8))
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @TestDescription("입력값이 허용되지 않는 경우")
    public void createEvent_Bad_Request_Wrong_Input() throws Exception {
        EventDto eventDto = EventDto.builder()
                .name("Spring")
                .description("Rest Api Development With Spring")
                .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 22, 11, 22, 33))
                .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 21, 11, 22, 33))
                .beginEventDateTime(LocalDateTime.of(2018, 11, 22, 11, 22, 33))
                .endEventDateTime(LocalDateTime.of(2018, 11, 21, 11, 22, 33))
                .basePrice(10000)
                .maxPrice(200)
                .limitOfEnrollment(100)
                .location("강남역 D2 스타트업 팩토리")
                .build();

        this.mockMvc.perform(post("/api/events")
                .contentType((MediaType.APPLICATION_JSON_UTF8))
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(eventDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].objectName").exists())
                .andExpect(jsonPath("$[0].field").exists())
                .andExpect(jsonPath("$[0].defaultMessage").exists())
                .andExpect(jsonPath("$[0].code").exists())
                ;
    }
}
