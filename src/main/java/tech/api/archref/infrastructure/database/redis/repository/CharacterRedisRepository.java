package tech.api.archref.infrastructure.database.redis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tech.api.archref.infrastructure.database.redis.entities.CharacterHash;

@Repository
public interface CharacterRedisRepository extends CrudRepository<CharacterHash, String> {
}
