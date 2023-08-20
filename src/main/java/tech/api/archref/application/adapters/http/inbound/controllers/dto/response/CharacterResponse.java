package tech.api.archref.application.adapters.http.inbound.controllers.dto.response;

import org.springframework.beans.BeanUtils;
import tech.api.archref.domain.entities.Character;
import tech.api.archref.domain.enums.Priority;
import tech.api.archref.domain.valueobjects.Address;

public record CharacterResponse(String id,
                                String name,
                                String description,
                                Integer attackPoint,
                                Address address,
                                Priority priority) {


    public static CharacterResponse from(final Character character) {
        var characterDto = new CharacterResponse(character.getId(),
                character.getName(),
                character.getDescription(),
                character.getAttackPoint(),
                character.getAddress(),
                character.getPriority());

        BeanUtils.copyProperties(character, characterDto);
        return characterDto;
    }
}
