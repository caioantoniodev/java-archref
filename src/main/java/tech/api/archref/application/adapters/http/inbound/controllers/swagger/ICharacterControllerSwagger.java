package tech.api.archref.application.adapters.http.inbound.controllers.swagger;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.request.CharacterCreateRequest;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.response.CharacterResponse;

@RequestMapping(value = "/v1/character", produces = {MediaType.APPLICATION_JSON_VALUE})
@Tag(name = "Character", description = "Operações referentes ao personagem")
public interface ICharacterControllerSwagger {

    @PostMapping
    @ApiResponse(responseCode = "201")
    ResponseEntity<CharacterResponse> post(@RequestBody @Valid CharacterCreateRequest characterCreateRequest);
}
