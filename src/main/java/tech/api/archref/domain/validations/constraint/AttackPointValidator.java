package tech.api.archref.domain.validations.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.request.CharacterCreateRequest;
import tech.api.archref.domain.enums.Priority;
import tech.api.archref.domain.validations.annotations.ValidateAttackPoint;

import static tech.api.archref.domain.exception.MessageErrorCodeConstants.INVALID_ATTACKPOINT_FIELD;

public class AttackPointValidator implements ConstraintValidator<ValidateAttackPoint, CharacterCreateRequest> {

    private boolean isValid;

    @Override
    public boolean isValid(CharacterCreateRequest characterCreateRequest, ConstraintValidatorContext constraintValidatorContext) {
        isValid = true;

        if (characterCreateRequest.attackPoint() != null) {
            if (Priority.NONE.equals(characterCreateRequest.priority())) {
                validateNonePriority(characterCreateRequest, constraintValidatorContext);
            }

            if (Priority.LOW.equals(characterCreateRequest.priority())) {
                validateLowPriority(characterCreateRequest, constraintValidatorContext);
            }

            if (Priority.MEDIUM.equals(characterCreateRequest.priority())) {
                validateMediumPriority(characterCreateRequest, constraintValidatorContext);
            }

            if (Priority.HIGH.equals(characterCreateRequest.priority())) {
                validateHighPriority(characterCreateRequest, constraintValidatorContext);
            }
        }

        return isValid;
    }

    private void validateNonePriority(CharacterCreateRequest characterCreateRequest, ConstraintValidatorContext constraintValidatorContext) {
        if (characterCreateRequest.attackPoint() >= 5) {
            addFieldListError(constraintValidatorContext);
        }
    }

    private void validateLowPriority(CharacterCreateRequest characterCreateRequest, ConstraintValidatorContext constraintValidatorContext) {
        if (characterCreateRequest.attackPoint() >= 7) {
            addFieldListError(constraintValidatorContext);
        }
    }

    private void validateMediumPriority(CharacterCreateRequest characterCreateRequest, ConstraintValidatorContext constraintValidatorContext) {
        if (characterCreateRequest.attackPoint() >= 9) {
            addFieldListError(constraintValidatorContext);
        }
    }

    private void validateHighPriority(CharacterCreateRequest characterCreateRequest, ConstraintValidatorContext constraintValidatorContext) {
        if (characterCreateRequest.attackPoint() >= 13) {
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
