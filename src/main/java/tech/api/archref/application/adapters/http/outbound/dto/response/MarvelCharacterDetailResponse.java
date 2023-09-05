package tech.api.archref.application.adapters.http.outbound.dto.response;

import java.time.ZonedDateTime;

public record MarvelCharacterDetailResponse(long id, String name, String description, ZonedDateTime modified) {

}
