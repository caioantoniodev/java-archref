package tech.api.archref.domain.services;

import tech.api.archref.application.adapters.http.inbound.controllers.dto.request.CharacterCreateRequest;

public interface ICharacterService {
    void create(CharacterCreateRequest characterCreateRequest);
}
