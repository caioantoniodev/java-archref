package tech.api.archref.application.adapters.http.inbound.controllers.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import tech.api.archref.domain.entities.Character;
import tech.api.archref.domain.enums.Priority;
import tech.api.archref.domain.validations.annotations.ValidateAttackPoint;
import tech.api.archref.domain.valueobjects.Address;

import static tech.api.archref.domain.exception.MessageErrorCodeConstants.FIELD_MAY_NOT_BE_NULL;

@ValidateAttackPoint
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record CharacterCreateRequest(@NotEmpty(message = FIELD_MAY_NOT_BE_NULL) String name,
                                     @NotEmpty(message = FIELD_MAY_NOT_BE_NULL) String description,
                                     @Min(1) @Max(13) @NotNull(message = FIELD_MAY_NOT_BE_NULL) Integer attackPoint,
                                     Address address,
                                     @NotNull(message = FIELD_MAY_NOT_BE_NULL) Priority priority) {
    public Character toCharacter() {
        Character character = new Character();
        BeanUtils.copyProperties(this, character);
        return character;
    }
}
