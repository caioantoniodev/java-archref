package tech.api.archref.application.adapters.amqp.character.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import tech.api.archref.config.amqp.RabbitMQConfig;
import tech.api.archref.config.application.MessageConfig;
import tech.api.archref.domain.entities.Character;
import tech.api.archref.utils.messages.MessageConstants;

@Component
public class CharacterPublisher {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final MessageConfig messageConfig;

    Logger LOGGER = LoggerFactory.getLogger(CharacterPublisher.class);

    public CharacterPublisher(MessageConfig messageConfig) {
        this.messageConfig = messageConfig;
    }

    public void sendMessage(Character character, HttpHeaders headers) {
        LOGGER.info(messageConfig.getMessage(MessageConstants.PUBLISHING_QUEUE), RabbitMQConfig.EXCHANGE);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, "", character);
    }
}
