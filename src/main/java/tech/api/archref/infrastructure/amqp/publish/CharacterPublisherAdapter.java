package tech.api.archref.infrastructure.amqp.publish;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import tech.api.archref.application.adapters.amqp.character.publisher.CharacterPublisher;
import tech.api.archref.domain.entities.Character;
import tech.api.archref.domain.ports.ICharacterMessageQueue;

@Configuration
public class CharacterPublisherAdapter implements ICharacterMessageQueue {

    private final CharacterPublisher characterPublisher;

    public CharacterPublisherAdapter(CharacterPublisher characterPublisher) {
        this.characterPublisher = characterPublisher;
    }

    @Override
    public void publishCharacterEvent(Character character, HttpHeaders headers) {
        characterPublisher.sendMessage(character, headers);
    }
}
