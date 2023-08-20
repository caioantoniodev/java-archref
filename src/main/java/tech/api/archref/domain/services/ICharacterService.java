package tech.api.archref.domain.services;

import tech.api.archref.application.adapters.http.inbound.controllers.dto.request.CharacterCreateRequest;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.response.CharacterResponse;

public interface ICharacterService {
    CharacterResponse create(CharacterCreateRequest characterCreateRequest);
}
