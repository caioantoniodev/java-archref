package tech.api.archref.application.adapters.http.inbound.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.request.CharacterCreateRequest;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.response.CharacterResponse;
import tech.api.archref.application.adapters.http.inbound.controllers.swagger.ICharacterControllerSwagger;
import tech.api.archref.domain.ports.ICharacterService;

@RestController
@AllArgsConstructor
public class CharacterController implements ICharacterControllerSwagger {

    private final ICharacterService characterService;

    @Override
    public ResponseEntity<CharacterResponse> post(CharacterCreateRequest characterCreateRequest) {
        var characterResponse = characterService.create(characterCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(characterResponse);
    }

    @Override
    public ResponseEntity<CharacterResponse> get(String id) {
        CharacterResponse characterResponse = characterService.getById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(characterResponse);
    }

    @Override
    public ResponseEntity<?> delete(String id) {
        characterService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
