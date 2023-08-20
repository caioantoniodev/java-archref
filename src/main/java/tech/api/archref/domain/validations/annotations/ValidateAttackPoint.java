package tech.api.archref.domain.validations.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import tech.api.archref.domain.validations.constraint.AttackPointValidator;

import java.lang.annotation.*;

import static tech.api.archref.domain.exception.MessageErrorCodeConstants.INVALID_ATTACKPOINT_FIELD;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AttackPointValidator.class)
public @interface ValidateAttackPoint {

    String message() default INVALID_ATTACKPOINT_FIELD;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
