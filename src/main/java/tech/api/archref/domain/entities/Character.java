package tech.api.archref.domain.entities;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import tech.api.archref.domain.enums.Priority;
import tech.api.archref.domain.valueobjects.Address;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "character")
public class Character extends IdentityEntity {

    private String name;

    private String description;

    private Integer attackPoint;

    private Address address;

    @Enumerated(EnumType.STRING)
    private Priority priority;
}
