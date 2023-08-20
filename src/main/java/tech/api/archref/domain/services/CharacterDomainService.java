package tech.api.archref.domain.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.request.CharacterCreateRequest;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.response.CharacterResponse;
import tech.api.archref.config.application.MessageConfig;
import tech.api.archref.domain.ports.ICharacterMessageQueue;
import tech.api.archref.infrastructure.database.mongo.ICharacterRepository;
import tech.api.archref.utils.messages.MessageConstants;


@Service
public class CharacterDomainService implements ICharacterService {

    private final ICharacterMessageQueue characterMessageQueue;
    private final ICharacterRepository characterRepository;
    private final MessageConfig messageConfig;

    Logger LOGGER = LoggerFactory.getLogger(CharacterDomainService.class);

    public CharacterDomainService(ICharacterMessageQueue characterMessageQueue, ICharacterRepository characterRepository, MessageConfig messageConfig) {
        this.characterMessageQueue = characterMessageQueue;
        this.characterRepository = characterRepository;
        this.messageConfig = messageConfig;
    }

    @Override
    public CharacterResponse create(CharacterCreateRequest characterCreateRequest) {
        LOGGER.info(messageConfig.getMessage(MessageConstants.CREATING));
        var character = characterCreateRequest.toCharacter();

        LOGGER.info(messageConfig.getMessage(MessageConstants.SAVING));
        var characterCreated = characterRepository.save(character);

        LOGGER.info(messageConfig.getMessage(MessageConstants.PUBLISHING));
        characterMessageQueue.publishCharacterEvent(characterCreated);

        return CharacterResponse.from(characterCreated);
    }
}
