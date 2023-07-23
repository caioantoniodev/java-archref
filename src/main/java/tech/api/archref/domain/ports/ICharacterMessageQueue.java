package tech.api.archref.domain.ports;

import org.springframework.http.HttpHeaders;
import tech.api.archref.domain.entities.Character;

public interface ICharacterMessageQueue {
    void publishCharacterEvent(Character character, HttpHeaders headers);
}
