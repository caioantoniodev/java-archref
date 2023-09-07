package tech.api.archref.domain.validations.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.request.CharacterCreateRequest;
import tech.api.archref.domain.validations.annotations.ValidateAttackPoint;

import static tech.api.archref.domain.exception.MessageErrorCodeConstants.INVALID_ATTACKPOINT_FIELD;

public class AttackPointValidator implements ConstraintValidator<ValidateAttackPoint, CharacterCreateRequest> {

    private boolean isValid;

    @Override
    public boolean isValid(CharacterCreateRequest characterCreateRequest, ConstraintValidatorContext constraintValidatorContext) {
        isValid = true;

        switch (characterCreateRequest.priority()) {
            case NONE -> validatePriority(characterCreateRequest.attackPoint(), 5, constraintValidatorContext);
            case LOW -> validatePriority(characterCreateRequest.attackPoint(), 7, constraintValidatorContext);
            case MEDIUM -> validatePriority(characterCreateRequest.attackPoint(), 9, constraintValidatorContext);
            case HIGH -> validatePriority(characterCreateRequest.attackPoint(), 13, constraintValidatorContext);
            default -> throw new IllegalStateException(String.format("Unexpected value: %s", characterCreateRequest.priority()));
        }

        return isValid;
    }

    private void validatePriority(Integer attackPoint, Integer limitPriority, ConstraintValidatorContext constraintValidatorContext) {
        if (new HasInvalidAttackPoint().test(attackPoint, limitPriority) ) {
            addFieldListError(constraintValidatorContext);
        }
    }

    private void addFieldListError(ConstraintValidatorContext context) {
        isValid = false;
        context.buildConstraintViolationWithTemplate(INVALID_ATTACKPOINT_FIELD)
                .addPropertyNode("attackPoint")
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

    }
}
