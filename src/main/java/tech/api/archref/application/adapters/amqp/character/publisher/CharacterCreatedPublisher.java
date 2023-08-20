package tech.api.archref.application.adapters.amqp.character.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.request.CharacterCreatedEvent;
import tech.api.archref.config.amqp.BrokerConfig;
import tech.api.archref.config.application.MessageConfig;
import tech.api.archref.domain.entities.Character;
import tech.api.archref.utils.messages.MessageConstants;

import java.util.UUID;

@Component
public class CharacterCreatedPublisher {

    @Autowired
    private final StreamBridge streamBridge;
    private final MessageConfig messageConfig;

    Logger LOGGER = LoggerFactory.getLogger(CharacterCreatedPublisher.class);

    public CharacterCreatedPublisher(StreamBridge streamBridge, MessageConfig messageConfig) {
        this.streamBridge = streamBridge;
        this.messageConfig = messageConfig;
    }

    public void sendMessage(Character character) {
        LOGGER.info(messageConfig.getMessage(MessageConstants.PUBLISHING_QUEUE), BrokerConfig.CHARACTER_CREATED_EVENT_CHANNEL);

        var characterCreatedEvent = new CharacterCreatedEvent(UUID.randomUUID().toString(), character);
        streamBridge.send(BrokerConfig.CHARACTER_CREATED_EVENT_CHANNEL, characterCreatedEvent);
    }
}
