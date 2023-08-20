package tech.api.archref.application.adapters.http.inbound.controllers.dto.response;

import tech.api.archref.config.application.MessageConfig;

public class ErrorResponse {

    private String code;
    private String message;

    public ErrorResponse(String code, Object... args) {
        this.code = code;
        this.message = new MessageConfig().getMessage(code, args);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}