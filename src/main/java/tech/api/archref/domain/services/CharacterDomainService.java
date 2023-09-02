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

import java.util.Optional;

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
        log.info(messageConfig.getMessage(MessageConstants.CREATING, characterCreateRequest));
        var character = characterCreateRequest.toCharacter();

        log.info(messageConfig.getMessage(MessageConstants.SAVING, character));
        var characterCreated = characterRepository.save(character);

        characterMessageQueue.publishCharacterEvent(characterCreated);

        return CharacterResponse.from(characterCreated);
    }

    @Override
    public CharacterResponse getById(String id) {
        var characterOptional = characterCache.findById(id);

        if (characterOptional.isEmpty()) {
            log.info(messageConfig.getMessage(MessageConstants.RESOURCE_NOT_FOUND_CACHE, Character.class.getName(), id));

            var optionalCharacter = findCharacterById(id);

            if (optionalCharacter.isPresent()) {
                log.info(messageConfig.getMessage(MessageConstants.RESOURCE_FOUND, Character.class.getName(), id));
                characterCache.save(optionalCharacter.get());
                return CharacterResponse.from(optionalCharacter.get());
            } else {
                throw new NotFoundException(CHARACTER_NOT_FOUND, messageConfig.getMessage(MessageConstants.RESOURCE_NOT_FOUND, Character.class.getName(), id));
            }
        }

        return CharacterResponse.from(characterOptional.get());
    }

    private Optional<Character> findCharacterById(String id) {
        return characterRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        log.info(messageConfig.getMessage(MessageConstants.EXCLUDING, id));

        var character = this.findCharacterById(id)
                .orElseThrow(() -> new NotFoundException(CHARACTER_NOT_FOUND,
                        messageConfig.getMessage(MessageConstants.RESOURCE_NOT_FOUND, Character.class.getName(), id)));

        log.info(messageConfig.getMessage(MessageConstants.RESOURCE_EXCLUDE, Character.class.getName(), id));
        characterRepository.deleteById(character.getId());
    }
}
