package tech.api.archref.application.adapters.amqp.character.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tech.api.archref.config.amqp.RabbitMQConfig;
import tech.api.archref.config.application.MessageConfig;
import tech.api.archref.domain.entities.Character;
import tech.api.archref.infrastructure.database.mongo.ICharacterRepository;
import tech.api.archref.utils.messages.MessageConstants;

@Component
public class CharacterProcessedConsumer {

    private final ICharacterRepository characterRepository;

    private final MessageConfig messageConfig;

    Logger LOGGER = LoggerFactory.getLogger(CharacterProcessedConsumer.class);

    public CharacterProcessedConsumer(ICharacterRepository characterRepository, MessageConfig messageConfig) {
        this.characterRepository = characterRepository;
        this.messageConfig = messageConfig;
    }

    @RabbitListener(queues = RabbitMQConfig.EXCHANGE)
    public void onCharacterProcessed(Character character) {
        LOGGER.info(messageConfig.getMessage(MessageConstants.SAVING));
        characterRepository.save(character);
    }
}
