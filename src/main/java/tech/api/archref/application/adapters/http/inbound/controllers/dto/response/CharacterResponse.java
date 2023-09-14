package tech.api.archref.application.adapters.http.inbound.controllers.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;
import org.springframework.hateoas.RepresentationModel;
import tech.api.archref.domain.entities.Character;
import tech.api.archref.domain.enums.Priority;
import tech.api.archref.domain.valueobjects.Address;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CharacterResponse extends RepresentationModel<CharacterResponse> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String description;
    private Integer attackPoint;
    private Address address;
    private Priority priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CharacterResponse from(final Character character) {
        var characterDto = new CharacterResponse(character.getId(),
                character.getName(),
                character.getDescription(),
                character.getAttackPoint(),
                character.getAddress(),
                character.getPriority(),
                character.getCreatedAt(),
                character.getUpdatedAt());

        BeanUtils.copyProperties(character, characterDto);
        return characterDto;
    }
}
