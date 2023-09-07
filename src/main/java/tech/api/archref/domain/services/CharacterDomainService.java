package tech.api.archref.domain.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.request.CharacterCreateRequest;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.response.CharacterResponse;
import tech.api.archref.application.adapters.http.outbound.ICharacterMarvelApi;
import tech.api.archref.application.adapters.http.outbound.dto.request.Params;
import tech.api.archref.config.application.ApplicationProps;
import tech.api.archref.config.application.MessageConfig;
import tech.api.archref.domain.entities.Character;
import tech.api.archref.domain.enums.Priority;
import tech.api.archref.domain.exception.NotFoundException;
import tech.api.archref.domain.ports.ICharacterCache;
import tech.api.archref.domain.ports.ICharacterMessageQueue;
import tech.api.archref.domain.ports.ICharacterService;
import tech.api.archref.domain.valueobjects.Address;
import tech.api.archref.infrastructure.database.mongo.ICharacterRepository;
import tech.api.archref.utils.messages.MessageConstants;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static tech.api.archref.domain.exception.MessageErrorCodeConstants.CHARACTER_NOT_FOUND;


@Service
@Slf4j
@AllArgsConstructor
public class CharacterDomainService implements ICharacterService {

    private final ICharacterMessageQueue characterMessageQueue;
    private final ICharacterRepository characterRepository;
    private final ICharacterCache characterCache;
    private final MessageConfig messageConfig;
    private final ICharacterMarvelApi characterMarvelApi;

    private ApplicationProps applicationProps;

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
        characterRepository.deleteById(String.valueOf(character.getId()));
    }

    @Override
    public CharacterResponse createRandom() {

        var characterIds = applicationProps.getCharacterIds();
        var randomCharacterId = this.retrieveRandomCharacterId(characterIds);

        var parameters = this.dealParameters();
        var characterCreateRequest = this.dealCharacterToCreate(randomCharacterId, parameters);

        return this.create(characterCreateRequest);
    }

    @Override
    public Page<CharacterResponse> getPages(Pageable pageable) {
        log.info(messageConfig.getMessage(MessageConstants.EXECUTING_QUERY));
        var characterPage = characterRepository.findAll(pageable);

        var characterResponses = characterPage.stream().map(CharacterResponse::from).toList();

        return null;
    }


    private CharacterCreateRequest dealCharacterToCreate(Long randomCharacterId, Params parameters) {
        var marvelCharacters = characterMarvelApi.RetrieveCharacterById(randomCharacterId, parameters.ts(), parameters.apiKey(), parameters.hash());

        var optionalMarvelCharacterDetailResponse = marvelCharacters.marvelCharacter().marvelCharacterDetails().stream().findFirst();

        if (optionalMarvelCharacterDetailResponse.isEmpty())
            throw new RuntimeException("Error");

        var marvelCharacterDetailResponse = optionalMarvelCharacterDetailResponse.get();
        var modified = marvelCharacterDetailResponse.modified().toLocalDateTime();

        return new CharacterCreateRequest(marvelCharacterDetailResponse.name(),
                marvelCharacterDetailResponse.description(),
                1,
                new Address(),
                Priority.NONE,
                modified,
                modified);
    }

    private Long retrieveRandomCharacterId(List<Long> characterIds) {
        Random rand = new Random();
        return characterIds.get(rand.nextInt(characterIds.size()));
    }

    private Params dealParameters() {

        var ts = UUID.randomUUID().toString();
        var hash = DigestUtils.md5Hex(String.format("%s%s%s", ts, applicationProps.getPrivateKey(), applicationProps.getPublicKey()));

        return new Params(ts, applicationProps.getPublicKey(), hash);
    }
}
