package com.websemantique.graphql.organiser;

import com.websemantique.graphql.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.Collection;

@Controller
@RequiredArgsConstructor
public class OrganiserController {

    private final OrganiserService organiserService;

    @QueryMapping
    public Collection<Organiser> organisers() {
        return organiserService.list();
    }

    @QueryMapping
    public Organiser organiserById(@Argument Long id) throws Exception {
        return organiserService.findById(id);
    }

    @MutationMapping
    public Organiser createOrganiser(@Argument Organiser.OrganiserInput organiserInput) {
        return organiserService.create(organiserInput);
    }

    @MutationMapping
    public Organiser modifyOrganiser(@Argument Long id, @Argument Organiser.OrganiserInput organiserInput) throws NotFoundException {
        Organiser organiser = organiserService.findById(id);
        return organiserService.modify(organiser, organiserInput);
    }

    @MutationMapping
    public Boolean deleteOrganiser(@Argument Long id) throws NotFoundException {
        Organiser organiser = organiserService.findById(id);
        organiserService.delete(organiser);
        return true;
    }


}
