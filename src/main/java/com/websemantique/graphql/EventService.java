package com.websemantique.graphql;

import com.websemantique.graphql.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public Collection<Event> list() {
        return eventRepository.findAll();
    }

    public Event findById(Long id) throws NotFoundException {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event with id #" + id + " not found"));
    }

    public Event create(Event.EventInput input) {
        return eventRepository.save(Event.builder()
                .name(input.name())
                .location(input.location())
                .description(input.description())
                .time(input.time())
                .type(input.type()).build());
    }

    public Event modify(Event event, Event.EventInput eventInput) {
        event.setName(eventInput.name());
        event.setDescription(eventInput.description());
        event.setType(eventInput.type());
        event.setLocation(eventInput.location());
        event.setTime(eventInput.time());
        return eventRepository.save(event);
    }

    public void delete(Event event){
        eventRepository.delete(event);
    }
}
