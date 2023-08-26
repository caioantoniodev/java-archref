package tech.api.archref.domain.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.request.CharacterCreateRequest;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.response.CharacterResponse;
import tech.api.archref.config.application.MessageConfig;
import tech.api.archref.domain.entities.Character;
import tech.api.archref.domain.exception.NotFoundException;
import tech.api.archref.domain.ports.ICharacterCache;
import tech.api.archref.domain.ports.ICharacterMessageQueue;
import tech.api.archref.domain.ports.ICharacterService;
import tech.api.archref.infrastructure.database.mongo.ICharacterRepository;
import tech.api.archref.utils.messages.MessageConstants;

import static tech.api.archref.domain.exception.MessageErrorCodeConstants.CHARACTER_NOT_FOUND;


@Service
@Slf4j
@RequiredArgsConstructor
public class CharacterDomainService implements ICharacterService {

    private final ICharacterMessageQueue characterMessageQueue;
    private final ICharacterRepository characterRepository;
    private final ICharacterCache characterCache;
    private final MessageConfig messageConfig;

    @Override
    public CharacterResponse create(CharacterCreateRequest characterCreateRequest) {
        log.info(messageConfig.getMessage(MessageConstants.CREATING));
        var character = characterCreateRequest.toCharacter();

        log.info(messageConfig.getMessage(MessageConstants.SAVING));
        var characterCreated = characterRepository.save(character);

        log.info(messageConfig.getMessage(MessageConstants.PUBLISHING));
        characterMessageQueue.publishCharacterEvent(characterCreated);

        return CharacterResponse.from(characterCreated);
    }

    @Override
    public CharacterResponse getById(String id) {
        var characterOptional = characterCache.findById(id);

        if (characterOptional.isEmpty()) {
            log.info(messageConfig.getMessage(MessageConstants.RESOURCE_NOT_FOUND_CACHE, Character.class.getName(), id));
            var characterRepositoryById = characterRepository.findById(id);

            if (characterRepositoryById.isPresent()) {
                log.info(messageConfig.getMessage(MessageConstants.RESOURCE_FOUND, Character.class.getName(), id));
                characterCache.save(characterRepositoryById.get());
                return CharacterResponse.from(characterRepositoryById.get());
            } else {
                throw new NotFoundException(CHARACTER_NOT_FOUND, messageConfig.getMessage(MessageConstants.RESOURCE_NOT_FOUND, Character.class.getName(), id));
            }
        }

        return CharacterResponse.from(characterOptional.get());
    }
}
