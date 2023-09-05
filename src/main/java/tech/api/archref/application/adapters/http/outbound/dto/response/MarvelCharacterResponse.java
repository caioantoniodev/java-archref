package tech.api.archref.application.adapters.http.outbound.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public record MarvelCharacterResponse(
        @JsonProperty("results") ArrayList<MarvelCharacterDetailResponse> marvelCharacterDetails) {
}
