package com.cardi.demoinflearnrestapi.events;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter @EqualsAndHashCode(of = "id")
@Entity
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
    private String location;    //(optional)
    private int basePrice;      //(optional)
    private int maxPrice;       //(optional)
    private int limitOfEnrollment;
    private boolean offline;
    private boolean free;

    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus = EventStatus.DRAFT;

    public void update() {
        //update Fee
        if(this.basePrice == 00 && this.maxPrice == 0){
            this.free = true;
        }else{
            this.free = false;
        }

        //update Location
        if(this.location == null || this.location.trim().isEmpty()){
            this.offline = false;
        }else{
            this.offline = true;
        }
    }
}
