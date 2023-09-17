package tech.api.archref.domain.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import tech.api.archref.config.application.ApplicationProps;
import tech.api.archref.config.application.MessageConfig;
import tech.api.archref.domain.ports.ICharacterCache;
import tech.api.archref.domain.ports.ICharacterMessageQueue;
import tech.api.archref.infrastructure.database.mongo.ICharacterRepository;
import tech.api.archref.mock.CharacterMock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {ApplicationProps.class})
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

    @Autowired
    private ApplicationProps applicationProps;

    @Test
    @DisplayName("CharacterService - Create")
    void shouldCreateCharacter() {
        // Given
        var characterCreateRequest = CharacterMock.validCharacterCreatedDto();

        when(characterRepository.save(any())).thenReturn(CharacterMock.validCharacter());

        // When
        var characterResponse = characterDomainService.create(characterCreateRequest);

        // Then
        assertEquals(characterCreateRequest.name(), characterResponse.getName());
        assertEquals(characterCreateRequest.description(), characterResponse.getDescription());
        assertEquals(characterCreateRequest.attackPoint(), characterResponse.getAttackPoint());
        assertEquals(characterCreateRequest.address(), characterResponse.getAddress());
        assertEquals(characterCreateRequest.priority(), characterResponse.getPriority());

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
        assertEquals(character.getName(), characterResponse.getName());
        assertEquals(character.getDescription(), characterResponse.getDescription());
        assertEquals(character.getAttackPoint(), characterResponse.getAttackPoint());
        assertEquals(character.getAddress(), characterResponse.getAddress());
        assertEquals(character.getPriority(), characterResponse.getPriority());

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
        assertEquals(character.getName(), characterResponse.getName());
        assertEquals(character.getDescription(), characterResponse.getDescription());
        assertEquals(character.getAttackPoint(), characterResponse.getAttackPoint());
        assertEquals(character.getAddress(), characterResponse.getAddress());
        assertEquals(character.getPriority(), characterResponse.getPriority());

        Mockito.verify(characterCache, Mockito.times(1)).findById(characterId);
        Mockito.verify(characterRepository, Mockito.times(1)).findById(characterId);
    }
}
