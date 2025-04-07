package com.websemantique.graphql.event;

import com.websemantique.graphql.exception.NotFoundException;
import com.websemantique.graphql.organiser.Organiser;
import com.websemantique.graphql.organiser.OrganiserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final OrganiserRepository organiserRepository;

    public Collection<Event> list() {
        return eventRepository.findAll();
    }

    public Event findById(Long id) throws NotFoundException {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event with id #" + id + " not found"));
    }

    public Event create(Event.EventInput input) throws NotFoundException {
        Event event = validateAndCreate(input);
        return eventRepository.save(event);
    }

    public Event modify(Event event, Event.EventInput eventInput) throws NotFoundException {
        Event newEvent = validateAndCreate(eventInput);

        event.setName(newEvent.getName());
        event.setDescription(newEvent.getDescription());
        event.setType(newEvent.getType());
        event.setLocation(newEvent.getLocation());
        event.setTime(newEvent.getTime());
        event.setOrganiser(newEvent.getOrganiser());

        return eventRepository.save(event);
    }

    public void delete(Event event) {
        eventRepository.delete(event);
    }

    private Event validateAndCreate(Event.EventInput input) throws NotFoundException {
        //Assertions if needed

        Organiser organiser = null;
        if (input.organiserId() != null) {
            organiser = organiserRepository.findById(input.organiserId())
                    .orElseThrow(() -> new NotFoundException("Organiser with id #" + input.organiserId() + " not found"));
        }
        return eventRepository.save(Event.builder()
                .name(input.name())
                .location(input.location())
                .description(input.description())
                .time(input.time())
                .type(input.type())
                .organiser(organiser)
                .build());
    }
}
