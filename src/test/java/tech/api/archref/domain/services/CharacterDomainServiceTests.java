package tech.api.archref.domain.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tech.api.archref.config.application.MessageConfig;
import tech.api.archref.domain.ports.ICharacterCache;
import tech.api.archref.domain.ports.ICharacterMessageQueue;
import tech.api.archref.infrastructure.database.mongo.ICharacterRepository;
import tech.api.archref.mock.CharacterMock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CharacterDomainServiceTests {

    @Mock
    private ICharacterRepository characterRepository;

    @Mock
    private ICharacterMessageQueue characterMessageQueue;

    @Mock
    private ICharacterCache characterCache;

    @Mock
    private MessageConfig messageConfig;

    @InjectMocks
    private CharacterDomainService characterDomainService;


    @Test
    @DisplayName("CharacterService - Create")
    void shouldCreateCharacter() {
        // Given
        var characterCreateRequest = CharacterMock.validCharacterCreatedDto();

        when(characterRepository.save(any())).thenReturn(CharacterMock.validCharacter());

        // When
        var characterResponse = characterDomainService.create(characterCreateRequest);

        // Then
        assertEquals(characterCreateRequest.name(), characterResponse.name());
        assertEquals(characterCreateRequest.description(), characterResponse.description());
        assertEquals(characterCreateRequest.attackPoint(), characterResponse.attackPoint());
        assertEquals(characterCreateRequest.address(), characterResponse.address());
        assertEquals(characterCreateRequest.priority(), characterResponse.priority());

        Mockito.verify(characterMessageQueue, Mockito.times(1)).publishCharacterEvent(any());
    }

    @Test
    @DisplayName("CharacterService - GetById with Cache")
    void ShouldGetCharacterWhenCacheReturnsAValidCharacter() {
        // Given
        var characterId = CharacterMock.validCharacter().getId();
        var character = CharacterMock.validCharacter();

        when(characterCache.findById(characterId)).thenReturn(Optional.of(character));

        // When
        var characterResponse = characterDomainService.getById(characterId);

        // Then
        assertEquals(character.getName(), characterResponse.name());
        assertEquals(character.getDescription(), characterResponse.description());
        assertEquals(character.getAttackPoint(), characterResponse.attackPoint());
        assertEquals(character.getAddress(), characterResponse.address());
        assertEquals(character.getPriority(), characterResponse.priority());

        Mockito.verify(characterCache, Mockito.times(1)).findById(characterId);
        Mockito.verify(characterRepository, Mockito.never()).findById(characterId);
    }

    @Test
    @DisplayName("CharacterService - GetById without Cache")
    void shouldGetCharacterWhenCharacterRepositoryReturnsAValidCharacter() {
        // Given
        var characterId = CharacterMock.validCharacter().getId();
        var character = CharacterMock.validCharacter();

        when(characterRepository.findById(characterId)).thenReturn(Optional.of(character));

        // When
        var characterResponse = characterDomainService.getById(characterId);

        // Then
        assertEquals(character.getName(), characterResponse.name());
        assertEquals(character.getDescription(), characterResponse.description());
        assertEquals(character.getAttackPoint(), characterResponse.attackPoint());
        assertEquals(character.getAddress(), characterResponse.address());
        assertEquals(character.getPriority(), characterResponse.priority());

        Mockito.verify(characterCache, Mockito.times(1)).findById(characterId);
        Mockito.verify(characterRepository, Mockito.times(1)).findById(characterId);
    }
}
