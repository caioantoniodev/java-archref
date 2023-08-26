package tech.api.archref.application.adapters.amqp.character.consumer;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.request.CharacterCreatedEvent;
import tech.api.archref.config.application.MessageConfig;
import tech.api.archref.utils.messages.MessageConstants;

import java.util.function.Consumer;

@Slf4j
@AllArgsConstructor
@Component
public class CharacterCreatedConsumer implements Consumer<CharacterCreatedEvent> {

    private final MessageConfig messageConfig;

    @Override
    public void accept(CharacterCreatedEvent characterCreatedEvent) {
        log.info(messageConfig.getMessage(MessageConstants.CONSUMED_QUEUE, characterCreatedEvent));
    }
}
