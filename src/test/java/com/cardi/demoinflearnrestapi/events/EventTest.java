package com.cardi.demoinflearnrestapi.events;


import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(JUnitParamsRunner.class)
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
//  Without Typesafe
//    @Parameters({
//            "0, 0, true",
//            "100, 0, false",
//            "0, 100, false"
//    })

    //For Typesafe using paramGenerator
    //@Parameters(method="methodName")

    //For Typesafe using default naming convention
    @Parameters
    public void testFree(int basePrice, int maxPrice, boolean isFree) {
        //Given
        Event event = Event.builder()
                .basePrice(basePrice)
                .maxPrice(maxPrice)
                .build();
        //When
        event.update();

        //Then
        Assertions.assertThat(event.isFree()).isEqualTo(isFree);
    }

    private Object[] parametersForTestFree(){
        return new Object[] {
                new Object[] {0, 0, true},
                new Object[] {100, 0, false},
                new Object[] {0, 100, false},
                new Object[] {100, 200, false}
        };
    }

    @Test
    @Parameters
    public void testOffline(String location, boolean isOffline) {
        //Given
        Event event = Event.builder()
                .location(location)
                .build();
        //When
        event.update();

        //Then
        Assertions.assertThat(event.isOffline()).isEqualTo(isOffline);
    }

    public Object[] parametersForTestOffline(){
        return new Object[]{
                new Object[]{"강남", true},
                new Object[]{null, false},
                new Object[]{"         ", false}
        };
    }
}