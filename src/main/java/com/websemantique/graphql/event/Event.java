package com.websemantique.graphql.event;

import com.websemantique.graphql.organiser.Organiser;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String type;
    private String location;
    private LocalDateTime time;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    private Organiser organiser;

    public record EventInput(
            String name,
            String description,
            String type,
            LocalDateTime time,
            String location,
            Long organiserId
    ) {
    }
}
