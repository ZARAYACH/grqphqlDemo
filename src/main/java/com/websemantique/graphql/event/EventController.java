package com.websemantique.graphql.event;

import com.websemantique.graphql.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @QueryMapping
    public Collection<Event> events() {
        return eventService.list();
    }

    @QueryMapping
    public Event eventById(@Argument Long id) throws Exception {
        return eventService.findById(id);
    }

    @MutationMapping
    public Event createEvent(@Argument Event.EventInput eventInput) throws NotFoundException {
        return eventService.create(eventInput);
    }

    @MutationMapping
    public Event modifyEvent(@Argument Long id, @Argument Event.EventInput eventInput) throws NotFoundException {
        Event event = eventService.findById(id);
        return eventService.modify(event, eventInput);
    }

    @MutationMapping
    public Boolean deleteEvent(@Argument Long id) throws NotFoundException {
        Event event = eventService.findById(id);
        eventService.delete(event);
        return true;
    }
}
