package tech.api.archref.infrastructure.database.redis.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import tech.api.archref.domain.enums.Priority;
import tech.api.archref.domain.valueobjects.Address;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "character", timeToLive = 720L)
public class CharacterHash {
    @Id
    private String id;

    private String name;

    private String description;

    private Integer attackPoint;

    private Address address;

    private Instant birth;

    private Priority priority;
}
