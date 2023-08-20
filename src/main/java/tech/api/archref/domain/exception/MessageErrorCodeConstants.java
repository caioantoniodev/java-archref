package tech.api.archref.domain.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageErrorCodeConstants {
    public static final String INVALID_REQUEST = "400.000";
    public static final String FIELD_INVALID = "400.001";
    public static final String FIELD_MAY_NOT_BE_NULL = "400.002";
    public static final String INVALID_ATTACKPOINT_FIELD = "400.003";
    public static final String CHARACTER_NOT_FOUND = "404.001";
    public static final String BUSINESS_ERROR = "422.000";
    public static final String INTERNAL_SERVER_ERROR = "500.000";
}
