package tech.api.archref.mock;

import tech.api.archref.application.adapters.http.inbound.controllers.dto.request.CharacterCreateRequest;
import tech.api.archref.domain.entities.Character;
import tech.api.archref.domain.enums.Priority;
import tech.api.archref.domain.valueobjects.Address;

import java.time.LocalDateTime;

public class CharacterMock {
    public static CharacterCreateRequest validCharacterCreatedDto() {

        var address = Address.builder()
                .city("City")
                .street("Street")
                .zipCode("36104")
                .build();

        return new CharacterCreateRequest("My Name",
                "My Description",
                1,
                address,
                Priority.NONE,
                null,
                null
        );
    }

    public static Character validCharacter() {
        return Character.builder()
                .id("64f7a256a7ecfb084f57137f")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .name("My Name")
                .description("My Description")
                .attackPoint(1)
                .address(Address.builder()
                        .city("City")
                        .street("Street")
                        .zipCode("36104")
                        .build())
                .priority(Priority.NONE)
                .build();
    }
}
