package tech.api.archref.infrastructure.amqp.publish;

import org.springframework.context.annotation.Configuration;
import tech.api.archref.application.adapters.amqp.character.publisher.CharacterCreatedPublisher;
import tech.api.archref.domain.entities.Character;
import tech.api.archref.domain.ports.ICharacterMessageQueue;

@Configuration
public class CharacterPublisherAdapter implements ICharacterMessageQueue {

    private final CharacterCreatedPublisher characterPublisher;

    public CharacterPublisherAdapter(CharacterCreatedPublisher characterPublisher) {
        this.characterPublisher = characterPublisher;
    }

    @Override
    public void publishCharacterEvent(Character character) {
        characterPublisher.sendMessage(character);
    }
}
