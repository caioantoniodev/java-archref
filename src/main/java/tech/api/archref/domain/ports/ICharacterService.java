package tech.api.archref.domain.ports;

import tech.api.archref.application.adapters.http.inbound.controllers.dto.request.CharacterCreateRequest;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.response.CharacterResponse;

public interface ICharacterService {
    CharacterResponse create(CharacterCreateRequest characterCreateRequest);

    CharacterResponse getById(String id);

    void delete(String id);
}
