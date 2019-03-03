package com.cardi.demoinflearnrestapi.events;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class EventResource extends Resource<Event> {

    public EventResource(Event event, Link... links) {
        super(event, links);
        add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
    }

    public void addQueryEventsLink(){
        add(linkTo(EventController.class).withRel("query-events"));

    }

    public void addUpdateEventsLink(ControllerLinkBuilder selfLinkBuilder){
        add(selfLinkBuilder.withRel("update-event"));
    }



}
