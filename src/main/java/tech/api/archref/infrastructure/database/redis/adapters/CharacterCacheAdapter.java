package tech.api.archref.infrastructure.database.redis.adapters;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import tech.api.archref.domain.entities.Character;
import tech.api.archref.domain.ports.ICharacterCache;
import tech.api.archref.infrastructure.database.redis.entities.CharacterHash;
import tech.api.archref.infrastructure.database.redis.repository.CharacterRedisRepository;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CharacterCacheAdapter implements ICharacterCache {

    private final CharacterRedisRepository characterRedisRepository;

    @Override
    public Character save(Character character) {
        var characterHash = new CharacterHash();

        BeanUtils.copyProperties(character, characterHash);
        BeanUtils.copyProperties(characterRedisRepository.save(characterHash), character);

        return character;
    }

    @Override
    public Optional<Character> findById(String id) {
        Optional<CharacterHash> characterHashOptional = characterRedisRepository.findById(id);

        Character character = null;
        if (characterHashOptional.isPresent()) {
            character = new Character();
            BeanUtils.copyProperties(characterHashOptional.get(), character);
        }

        return Optional.ofNullable(character);
    }
}
