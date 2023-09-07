package tech.api.archref.domain.validations.constraint;

import java.util.function.BiPredicate;

public class HasInvalidAttackPoint implements BiPredicate<Integer, Integer> {

    @Override
    public boolean test(Integer attackPoint, Integer limitPriority) {
        return attackPoint > limitPriority;
    }
}
