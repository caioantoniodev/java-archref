package tech.api.archref.application.adapters.http.inbound.controllers.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import tech.api.archref.config.application.MessageConfig;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {

    private String code;
    private String message;

    public ErrorResponse(String code, Object... args) {
        this.code = code;
        this.message = new MessageConfig().getMessage(code, args);
    }
}