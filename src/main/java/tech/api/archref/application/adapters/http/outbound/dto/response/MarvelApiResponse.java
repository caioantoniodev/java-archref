package tech.api.archref.application.adapters.http.outbound.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MarvelApiResponse(@JsonProperty("data") MarvelCharacterResponse marvelCharacter) {
}

