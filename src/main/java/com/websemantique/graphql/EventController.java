package com.websemantique.graphql;

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
    public Event create(@Argument Event.EventInput eventInput) {
        return eventService.create(eventInput);
    }

    @MutationMapping
    public Event modify(@Argument Long id, @Argument Event.EventInput eventInput) throws NotFoundException {
        Event event = eventService.findById(id);
        return eventService.modify(event, eventInput);
    }

    @MutationMapping
    public Boolean delete(@Argument Long id) throws NotFoundException {
        Event event = eventService.findById(id);
        eventService.delete(event);
        return true;
    }


}
