package tech.api.archref.application.adapters.http.inbound.controllers.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import tech.api.archref.domain.entities.Character;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CharacterCreatedEvent(String eventId, Character character) {
}
