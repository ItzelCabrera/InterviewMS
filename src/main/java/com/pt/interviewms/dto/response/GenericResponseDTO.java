package com.pt.interviewms.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pt.interviewms.models.commons.CommonResponse;
import java.io.Serializable;

public class GenericResponseDTO <T> extends CommonResponse implements Serializable {
    @JsonInclude(JsonInclude.Include.NON_NULL) //excluye este campo si es nulo
    @JsonProperty("body")
    private T body;
    public GenericResponseDTO(boolean success, Integer httpstatus, String errorCode, String errorMessage, String message,
                              T body) {
        super(success, httpstatus, errorCode, errorMessage, message);
        this.body = body;
    }
}
