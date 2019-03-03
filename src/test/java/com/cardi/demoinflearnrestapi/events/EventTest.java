package com.cardi.demoinflearnrestapi.events;


import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EventTest {
    @Test
    public void builder() {
        Event event = Event.builder()
                .name("Inflearn Spring Rest Api")
                .description("Rest API Development With Spring")
                .build();
        assertThat(event).isNotNull();
    }

    @Test
    public void javaBean() {
        //Given
        String name = "Event";
        String description = "Spring";

        //When
        Event event = new Event();
        event.setName(name);
        event.setDescription(description);

        //Then
        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);
    }



    @Test
    public void testFree() {
        //Given
        Event event = Event.builder()
                .basePrice(0)
                .maxPrice(0)
                .build();
        //When
        event.update();

        //Then
        Assertions.assertThat(event.isFree()).isTrue();

        //Given
        event = Event.builder()
                .basePrice(0)
                .maxPrice(100)
                .build();
        //When
        event.update();

        //Then
        Assertions.assertThat(event.isFree()).isFalse();
    }

    @Test
    public void testOffline() {
        //Given
        Event event = Event.builder()
                .location("강남역 네이버")
                .build();
        //When
        event.update();

        //Then
        Assertions.assertThat(event.isOffline()).isTrue();

        //Given
        event = Event.builder()
                .build();
        //When
        event.update();

        //Then
        Assertions.assertThat(event.isOffline()).isFalse();

    }
}