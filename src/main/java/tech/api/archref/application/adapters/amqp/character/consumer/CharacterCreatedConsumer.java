package tech.api.archref.application.adapters.amqp.character.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.request.CharacterCreatedEvent;
import tech.api.archref.config.application.MessageConfig;
import tech.api.archref.utils.messages.MessageConstants;

import java.util.function.Consumer;

@Component
public class CharacterCreatedConsumer implements Consumer<CharacterCreatedEvent> {

    private final MessageConfig messageConfig;

    Logger LOGGER = LoggerFactory.getLogger(CharacterCreatedConsumer.class);

    public CharacterCreatedConsumer(MessageConfig messageConfig) {
        this.messageConfig = messageConfig;
    }

    @Override
    public void accept(CharacterCreatedEvent characterCreatedEvent) {
        LOGGER.info(messageConfig.getMessage(MessageConstants.CONSUMED_QUEUE), characterCreatedEvent);
    }
}
