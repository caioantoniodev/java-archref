package tech.api.archref.domain.entities;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import tech.api.archref.domain.enums.Priority;
import tech.api.archref.domain.valueobjects.Address;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "character")
public class Character {

    @Id
    private String id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String name;

    private String description;

    private Integer attackPoint;

    private Address address;

    @Enumerated(EnumType.STRING)
    private Priority priority;
}
