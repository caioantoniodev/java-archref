package tech.api.archref.domain.ports;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.request.CharacterCreateRequest;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.response.CharacterResponse;


public interface ICharacterService {
    CharacterResponse create(CharacterCreateRequest characterCreateRequest);

    CharacterResponse getById(String id);

    void delete(String id);

    CharacterResponse createRandom();

    Page<CharacterResponse> getPages(Pageable pageable);
}
