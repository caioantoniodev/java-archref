package tech.api.archref.infrastructure.database.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tech.api.archref.domain.entities.Character;

@Repository
public interface ICharacterRepository extends MongoRepository<Character, String> {
}
