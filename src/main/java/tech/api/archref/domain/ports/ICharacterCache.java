package tech.api.archref.domain.ports;

import tech.api.archref.domain.entities.Character;

import java.util.Optional;

public interface ICharacterCache {
    Character save(Character character);

    Optional<Character> findById(String id);
}
