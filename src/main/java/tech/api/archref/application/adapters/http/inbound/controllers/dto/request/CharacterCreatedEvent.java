package tech.api.archref.application.adapters.http.inbound.controllers.dto.request;

import tech.api.archref.domain.entities.Character;

public record CharacterCreatedEvent(String eventId, Character character) {
}
