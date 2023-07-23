package tech.api.archref.application.adapters.http.inbound.controllers.dto.request;


import org.springframework.beans.BeanUtils;
import tech.api.archref.domain.entities.Character;

public record CharacterCreateRequest(String name,
                                     String description,
                                     Integer attackPoint,
                                     String address) {
    public Character toCharacter() {
        Character character = new Character();
        BeanUtils.copyProperties(this, character);
        return character;
    }
}
