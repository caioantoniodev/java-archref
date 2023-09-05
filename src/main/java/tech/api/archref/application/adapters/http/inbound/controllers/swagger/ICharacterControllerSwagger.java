package tech.api.archref.application.adapters.http.inbound.controllers.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.request.CharacterCreateRequest;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.response.CharacterResponse;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.response.ErrorResponse;

@RequestMapping(value = "/v1/character", produces = {MediaType.APPLICATION_JSON_VALUE})
@Tag(name = "Character", description = "Operações referentes ao personagem")
public interface ICharacterControllerSwagger {

    @PostMapping
    @Operation(description = "Criação de um personagem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Criado"),
            @ApiResponse(responseCode = "400", description = "Erro de validação do contrato enviado.", content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))
            }),
            @ApiResponse(responseCode = "422", description = "Entidade não processada.", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ou serviço.", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    ResponseEntity<CharacterResponse> post(@RequestBody @Valid CharacterCreateRequest characterCreateRequest);


    @GetMapping("/{id}")
    @Operation(description = "Busca de um personagem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personagem Encontrado"),
            @ApiResponse(responseCode = "404", description = "Personagem não encontrada.", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor ou serviço.", content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))
            })
    })
    ResponseEntity<CharacterResponse> get(@PathVariable("id") String id);

    @DeleteMapping("/{id}")
    @Operation(description = "Deletar um personagem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deletado."),
    })
    ResponseEntity<?> delete(@PathVariable("id") String id);

    @PostMapping("/random")
    ResponseEntity<?> postRandom();
}
