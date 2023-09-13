package tech.api.archref.application.adapters.http.inbound.controllers.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.beans.BeanUtils;
import tech.api.archref.domain.entities.Character;
import tech.api.archref.domain.enums.Priority;
import tech.api.archref.domain.valueobjects.Address;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CharacterResponse(String id,
                                String name,
                                String description,
                                Integer attackPoint,
                                Address address,
                                Priority priority,
                                LocalDateTime createdAt,
                                LocalDateTime updatedAt) {


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
