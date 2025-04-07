package com.websemantique.graphql.organiser;


import com.websemantique.graphql.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class OrganiserService {

    private final OrganiserRepository organiserRepository;

    public Collection<Organiser> list() {
        return organiserRepository.findAll();
    }

    public Organiser findById(Long id) throws NotFoundException {
        return organiserRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Organiser with id #" + id + " not found"));
    }

    public Organiser create(Organiser.OrganiserInput input) {
        return organiserRepository.save(Organiser.builder()
                .name(input.name()).build());
    }

    public Organiser modify(Organiser event, Organiser.OrganiserInput eventInput) {
        event.setName(eventInput.name());
        return organiserRepository.save(event);
    }

    public void delete(Organiser event) {
        organiserRepository.delete(event);
    }
}
