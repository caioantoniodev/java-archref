package tech.api.archref.application.adapters.http.inbound.controllers.swagger;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.request.CharacterCreateRequest;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.response.CharacterResponseResponse;

@RequestMapping(value = "/v1/character", produces = {MediaType.APPLICATION_JSON_VALUE})
@Tag(name = "Character", description = "Operações referentes ao personagem")
public interface ICharacterControllerSwagger {


    @ApiOperation(value = "Criação de um Character")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Criado", response = CharacterResponseResponse.class),
            @ApiResponse(code = 422, message = "Entidade não processada"),
            @ApiResponse(code = 500, message = "Erro interno do servidor ou serviço."),
    })
    @PostMapping
    ResponseEntity<?> post(@RequestBody @Valid CharacterCreateRequest characterCreateRequest);
}
