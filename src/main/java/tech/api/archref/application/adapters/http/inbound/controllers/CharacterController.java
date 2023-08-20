package tech.api.archref.application.adapters.http.inbound.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.request.CharacterCreateRequest;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.response.CharacterResponse;
import tech.api.archref.application.adapters.http.inbound.controllers.swagger.ICharacterControllerSwagger;
import tech.api.archref.domain.services.ICharacterService;

@RestController
public class CharacterController implements ICharacterControllerSwagger {

    private final ICharacterService characterService;

    public CharacterController(ICharacterService characterService) {
        this.characterService = characterService;
    }

    @Override
    public ResponseEntity<CharacterResponse> post(CharacterCreateRequest characterCreateRequest) {
        characterService.create(characterCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
