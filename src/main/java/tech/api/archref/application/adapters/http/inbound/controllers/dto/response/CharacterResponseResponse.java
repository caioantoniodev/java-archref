package tech.api.archref.application.adapters.http.inbound.controllers.dto.response;

import org.springframework.beans.BeanUtils;
import tech.api.archref.domain.entities.Character;

public record CharacterResponseResponse(String id,
                                        String name,
                                        String description,
                                        Integer attackPoint,
                                        String address) {


    public static CharacterResponseResponse from(final Character character) {
        var characterDto = new CharacterResponseResponse(character.getId(),
                character.getName(),
                character.getDescription(),
                character.getAttackPoint(),
                character.getAddress());

        BeanUtils.copyProperties(character, characterDto);
        return characterDto;
    }
}
