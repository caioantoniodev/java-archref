package tech.api.archref.domain.ports;

import tech.api.archref.domain.entities.Character;

public interface ICharacterMessageQueue {
    void publishCharacterEvent(Character character);
}
