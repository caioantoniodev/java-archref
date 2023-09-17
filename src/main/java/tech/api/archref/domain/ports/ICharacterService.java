package tech.api.archref.domain.ports;

import org.springframework.data.domain.Pageable;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.request.CharacterCreateRequest;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.response.CharacterResponse;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.response.pageable.PageableResponse;

import java.util.Map;


public interface ICharacterService {
    CharacterResponse create(CharacterCreateRequest characterCreateRequest);

    CharacterResponse getById(String id);

    void delete(String id);

    CharacterResponse createRandom();

    PageableResponse<CharacterResponse> getPages(Pageable pageable);

    boolean updatePartialUser(String id, Map<String, Object> requestData);
}
