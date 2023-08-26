package tech.api.archref.application.adapters.amqp.character.publisher;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.request.CharacterCreatedEvent;
import tech.api.archref.config.amqp.BrokerConfig;
import tech.api.archref.config.application.MessageConfig;
import tech.api.archref.domain.entities.Character;
import tech.api.archref.utils.messages.MessageConstants;

import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class CharacterCreatedPublisher {

    private final StreamBridge streamBridge;
    private final MessageConfig messageConfig;

    public void sendMessage(Character character) {
        log.info(messageConfig.getMessage(MessageConstants.PUBLISHING_QUEUE, BrokerConfig.CHARACTER_CREATED_EVENT_CHANNEL));

        var characterCreatedEvent = new CharacterCreatedEvent(UUID.randomUUID().toString(), character);
        streamBridge.send(BrokerConfig.CHARACTER_CREATED_EVENT_CHANNEL, characterCreatedEvent);
    }
}
