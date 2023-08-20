package tech.api.archref.domain.entities;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import tech.api.archref.domain.enums.Priority;
import tech.api.archref.domain.valueobjects.Address;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "character")
public class Character {

    private String id;

    private String name;

    private String description;

    private Integer attackPoint;

    private Address address;

    @Enumerated(EnumType.STRING)
    private Priority priority;
}
