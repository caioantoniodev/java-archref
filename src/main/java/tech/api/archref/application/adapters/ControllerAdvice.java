package tech.api.archref.application.adapters;

import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import tech.api.archref.application.adapters.http.inbound.controllers.dto.response.ErrorResponse;
import tech.api.archref.domain.exception.RestException;

import java.util.*;
import java.util.stream.Collectors;

import static tech.api.archref.domain.exception.MessageErrorCodeConstants.FIELD_INVALID;
import static tech.api.archref.domain.exception.MessageErrorCodeConstants.INVALID_REQUEST;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvice {

    private static final Integer JVM_MAX_STRING_LEN = Integer.MAX_VALUE;

    private final MessageSource messageSource;

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Void> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        e.printStackTrace();
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<List<ErrorResponse>> methodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException e) {
        e.printStackTrace();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.singletonList(ErrorResponse.builder()
                .code(FIELD_INVALID)
                .message(getMessage(FIELD_INVALID, e.getName()))
                .build()));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Error> mediaTypeNotFoundException(final HttpMediaTypeNotSupportedException e) {
        e.printStackTrace();

        return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<List<ErrorResponse>> assertionException(final HttpMessageNotReadableException e) {
        e.printStackTrace();

        if (e.getCause() instanceof JsonMappingException cause) {
            String field = cause.getPath().stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .filter(StringUtils::isNotBlank)
                    .collect(Collectors.joining("."));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonList(ErrorResponse.builder()
                            .code(FIELD_INVALID)
                            .message(getMessage(FIELD_INVALID, field))
                            .build()));
        }

        return defaultBadRequestError();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<List<ErrorResponse>> missingServletRequestParameterException(
            final MissingServletRequestParameterException e) {
        e.printStackTrace();

        return defaultBadRequestError();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponse>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException methodArgumentNotValidException) {
        List<ErrorResponse> messageErrors = Optional.ofNullable(methodArgumentNotValidException)
                .filter(argumentNotValidException -> !ObjectUtils.isEmpty(argumentNotValidException.getBindingResult()))
                .map(MethodArgumentNotValidException::getBindingResult)
                .filter(bindingResult -> !ObjectUtils.isEmpty(bindingResult.getAllErrors()))
                .map(BindingResult::getAllErrors)
                .stream()
                .flatMap(Collection::stream)
                .filter(objectError -> !ObjectUtils.isEmpty(objectError))
                .map(this::createError)
                .toList();
        return new ResponseEntity<>(messageErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ErrorResponse>> handleConstraintViolationException(
            ConstraintViolationException e) {
        e.printStackTrace();

        List<ErrorResponse> errors = e.getConstraintViolations().stream()
                .map(constraint -> new ErrorResponse(constraint.getMessageTemplate(),
                        ((PathImpl) constraint.getPropertyPath()).getLeafNode()))
                .toList();
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception exception) {
        exception.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(RestException.class)
    public ResponseEntity<Object> handleRestException(RestException restException) {
        restException.printStackTrace();

        if (restException.getResponseBodyCode() != null) {
            return ResponseEntity
                    .status(restException.getStatus())
                    .body(new ErrorResponse(restException.getResponseBodyCode(), getMessage(restException.getResponseBodyCode())));
        }

        if (restException.getResponseBody() != null) {
            String message = StringUtils.isBlank(restException.getResponseBody().getMessage())
                    ? getMessage(restException.getResponseBody().getCode())
                    : restException.getResponseBody().getMessage();

            return ResponseEntity
                    .status(restException.getStatus())
                    .body(new ErrorResponse(restException.getResponseBody().getCode(), message));
        }

        return ResponseEntity.status(restException.getStatus()).build();
    }

    private String getMessage(String code, Object... args) {
        return this.messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    private ResponseEntity<List<ErrorResponse>> defaultBadRequestError() {
        return new ResponseEntity<>(
                Collections.singletonList(ErrorResponse.builder()
                        .code(INVALID_REQUEST)
                        .message(getMessage(INVALID_REQUEST))
                        .build()),
                HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse createError(ObjectError error) {
        String field = "";
        if (error instanceof FieldError fieldError) {
            field = fieldError.getField();
        }

        if (Objects.equals(error.getCode(), "Size")) {
            Integer min = null;
            Integer max = null;
            if (Objects.requireNonNull(error.getArguments()).length > 2) {
                List<Object> argumentsList = Arrays.stream(error.getArguments()).toList();

                Integer rawMax = (Integer) argumentsList.get(1);
                max = Objects.equals(rawMax, JVM_MAX_STRING_LEN) ? null : rawMax;

                Integer rawMin = (Integer) argumentsList.get(2);
                min = rawMin == 0 ? null : rawMin;
            }

            if (min != null && max != null) {
                return new ErrorResponse(error.getDefaultMessage(), field, min, max);
            } else if (min != null) {
                return new ErrorResponse(error.getDefaultMessage(), field, min);
            } else if (max != null) {
                return new ErrorResponse(error.getDefaultMessage(), field, max);
            }
        }

        return ErrorResponse.builder()
                .code(error.getDefaultMessage())
                .message(getMessage(error.getDefaultMessage(), field))
                .build();
    }
}